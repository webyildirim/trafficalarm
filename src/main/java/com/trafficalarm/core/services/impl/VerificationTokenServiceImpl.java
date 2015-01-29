package com.trafficalarm.core.services.impl;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Validator;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.VerificationToken;
import com.trafficalarm.core.model.entities.VerificationTokenType;
import com.trafficalarm.core.repositories.AccountRepo;
import com.trafficalarm.core.repositories.VerificationTokenRepo;
import com.trafficalarm.core.services.VerificationTokenService;
import com.trafficalarm.core.services.base.BaseService;
import com.trafficalarm.core.services.mail.EmailServiceTokenModel;
import com.trafficalarm.core.services.mail.MailSenderService;
import com.trafficalarm.rest.api.user.LostPasswordRequest;
import com.trafficalarm.rest.api.user.PasswordRequest;
import com.trafficalarm.rest.exceptions.user.AlreadyVerifiedException;
import com.trafficalarm.rest.exceptions.user.AuthenticationException;
import com.trafficalarm.rest.exceptions.user.TokenHasExpiredException;
import com.trafficalarm.rest.exceptions.user.TokenNotFoundException;
import com.trafficalarm.rest.exceptions.user.UserNotFoundException;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
@Service
public class VerificationTokenServiceImpl extends BaseService implements VerificationTokenService {

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$");

    private VerificationTokenRepo tokenRepository;

    private MailSenderService mailSenderService;

    private AccountRepo userRepository;

    private PasswordEncoder passwordEncoder;

    @Value("${token.emailVerification.timeToLive.inMinutes}")
    private int emailVerificationTokenExpiryTimeInMinutes;

    @Value("${token.emailRegistration.timeToLive.inMinutes}")
    private int emailRegistrationTokenExpiryTimeInMinutes;

    @Value("${token.lostPassword.timeToLive.inMinutes}")
    private int lostPasswordTokenExpiryTimeInMinutes;

    @Value("${hostName.url}")
    private String hostNameUrl;

    public VerificationTokenServiceImpl(Validator validator) {
        super(validator);
    }

    @Autowired
    public VerificationTokenServiceImpl(AccountRepo userRepository, VerificationTokenRepo tokenRepository,
                                        MailSenderService mailSenderService, Validator validator, PasswordEncoder passwordEncoder) {
        this(validator);
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.mailSenderService = mailSenderService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Async
    public VerificationToken sendEmailVerificationToken(String userId) {
        Account user = ensureUserIsLoaded(userId);
        return sendEmailVerificationToken(user);
    }

    private VerificationToken sendEmailVerificationToken(Account user) {
        VerificationToken token = new VerificationToken(user,
                VerificationTokenType.emailVerification, emailVerificationTokenExpiryTimeInMinutes);
        try
		{
			tokenRepository.save(token);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        mailSenderService.sendVerificationEmail(new EmailServiceTokenModel(user,
                token, hostNameUrl));
        return token;
    }

    @Transactional
    @Async
    public VerificationToken sendEmailRegistrationToken(String userId) {
        Account user = ensureUserIsLoaded(userId);
        VerificationToken token = new VerificationToken(user,
                VerificationTokenType.emailRegistration, emailRegistrationTokenExpiryTimeInMinutes);
        try
		{
			tokenRepository.save(token);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        mailSenderService.sendRegistrationEmail(new EmailServiceTokenModel(user,
                token, hostNameUrl));
        return token;
    }

    /**
     * generate token if user found otherwise do nothing
     *
     * @param lostPasswordRequest
     * @return a token or null if user not found
     */
    @Transactional
    @Async
    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest) {
        validate(lostPasswordRequest);
        VerificationToken token = null;
        Account user = userRepository.findAccountByName(lostPasswordRequest.getEmailAddress());
        if (user != null) {
            List<VerificationToken> tokens = tokenRepository.findByUserIdAndTokenType(user.getId(), VerificationTokenType.lostPassword);
            token = getActiveToken(tokens);
            if (token == null) {
                token = new VerificationToken(user,
                VerificationTokenType.lostPassword, lostPasswordTokenExpiryTimeInMinutes);
                try
				{
					tokenRepository.save(token);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            mailSenderService.sendLostPasswordEmail(new EmailServiceTokenModel(user, token, hostNameUrl));

        }

        return token;
    }

    @Transactional
    public VerificationToken verify(String base64EncodedToken) {
        VerificationToken token = loadToken(base64EncodedToken);
        Account user = userRepository.findAccount(token.getAccount().getId());
        if (token.isVerified() || !user.isPassive()) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        user.setPassive(false);
        try
		{
			userRepository.createAccount(user);
	        tokenRepository.save(token);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return token;
    }

    @Transactional
    public VerificationToken generateEmailVerificationToken(String emailAddress) {
        Assert.notNull(emailAddress);
        Account user = userRepository.findAccountByName(emailAddress);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (!user.isPassive()) {
            throw new AlreadyVerifiedException();
        }
        //if token still active resend that
        VerificationToken token = getActiveToken(tokenRepository.findByUserIdAndTokenType(user.getId(), VerificationTokenType.emailVerification));
        if (token == null) {
            token = sendEmailVerificationToken(user);
        } else {
            mailSenderService.sendVerificationEmail(new EmailServiceTokenModel(user, token, hostNameUrl));
        }
        return token;
    }

    @Transactional
    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest) {
        Assert.notNull(base64EncodedToken);
        validate(passwordRequest);
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified()) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        Account user = userRepository.findAccount(token.getAccount().getId());
        try {
            user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        //set user to verified if not already and authenticated role
        user.setPassive(false);
        try
		{
			userRepository.createAccount(user);
	        tokenRepository.save(token);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        return token;
    }

    private VerificationToken loadToken(String base64EncodedToken) {
        Assert.notNull(base64EncodedToken);
        String rawToken = new String(Base64.decodeBase64(base64EncodedToken.getBytes()));
        VerificationToken token = tokenRepository.findByToken(rawToken);
        if (token == null) {
            throw new TokenNotFoundException();
        }
        if (token.hasExpired()) {
            throw new TokenHasExpiredException();
        }
        return token;
    }


    @Autowired
    public void setUserRepository(AccountRepo userRepository) {
        this.userRepository = userRepository;
    }


    private Account ensureUserIsLoaded(String userIdentifier) {
        Account user = null;
        if (isValidUuid(userIdentifier)) {
            user = userRepository.findAccount(userIdentifier);
        } else {
            user = userRepository.findAccountByName(userIdentifier);
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    private VerificationToken getActiveToken(List<VerificationToken> tokens) {
        VerificationToken activeToken = null;
        for (VerificationToken token : tokens) {
            if (!token.hasExpired() && !token.isVerified()) {
                activeToken = token;
                break;
            }
        }
        return activeToken;
    }

    private boolean isValidUuid(String uuid) {
        return UUID_PATTERN.matcher(uuid).matches();
    }

    public void setEmailVerificationTokenExpiryTimeInMinutes(int emailVerificationTokenExpiryTimeInMinutes) {
        this.emailVerificationTokenExpiryTimeInMinutes = emailVerificationTokenExpiryTimeInMinutes;
    }

    public void setEmailRegistrationTokenExpiryTimeInMinutes(int emailRegistrationTokenExpiryTimeInMinutes) {
        this.emailRegistrationTokenExpiryTimeInMinutes = emailRegistrationTokenExpiryTimeInMinutes;
    }

    public void setLostPasswordTokenExpiryTimeInMinutes(int lostPasswordTokenExpiryTimeInMinutes) {
        this.lostPasswordTokenExpiryTimeInMinutes = lostPasswordTokenExpiryTimeInMinutes;
    }

    public void setHostNameUrl(String hostNameUrl) {
        this.hostNameUrl = hostNameUrl;
    }
}
