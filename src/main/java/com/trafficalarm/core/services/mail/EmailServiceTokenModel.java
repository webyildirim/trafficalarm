package com.trafficalarm.core.services.mail;

import java.io.Serializable;

import org.apache.commons.codec.binary.Base64;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.VerificationToken;
import com.trafficalarm.core.model.entities.VerificationTokenType;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class EmailServiceTokenModel implements Serializable {

    private final String emailAddress;
    private final String token;
    private final VerificationTokenType tokenType;
    private final String hostNameUrl;


    public EmailServiceTokenModel(Account user, VerificationToken token, String hostNameUrl)  {
        this.emailAddress = user.getName();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEncodedToken() {
        return new String(Base64.encodeBase64(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public VerificationTokenType getTokenType() {
        return tokenType;
    }

    public String getHostNameUrl() {
        return hostNameUrl;
    }
}

