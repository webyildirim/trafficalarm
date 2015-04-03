package com.trafficalarm.rest.configuration;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import com.trafficalarm.core.repositories.AccountRepo;
import com.trafficalarm.core.repositories.VerificationTokenRepo;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.VerificationTokenService;
import com.trafficalarm.core.services.impl.AccountServiceImpl;
import com.trafficalarm.core.services.impl.VerificationTokenServiceImpl;
import com.trafficalarm.core.services.mail.MailSenderService;
import com.trafficalarm.rest.mvc.AccountController;
import com.trafficalarm.rest.mvc.MeResource;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class UserConfiguration {
    
    @Autowired
    private AccountRepo userRepository;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private Validator validator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DefaultTokenServices tokenServices;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    public VerificationTokenService verificationTokenService() {
        return new VerificationTokenServiceImpl(userRepository, verificationTokenRepo, mailSenderService, validator, passwordEncoder);
    }
    
    @Bean
    public AccountService userService() {
        return new AccountServiceImpl(userRepository, validator, passwordEncoder);
    } 
    
    @Bean
    public AccountController userResource() {
        return new AccountController(userService(), verificationTokenService(), tokenServices, passwordEncoder, clientDetailsService);
    }

    @Bean
    public MeResource meResource() {
        return new MeResource();
    }

}
