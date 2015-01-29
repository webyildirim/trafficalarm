package com.trafficalarm.rest.api.user;

import static org.springframework.util.Assert.notNull;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 24/04/2013
 */
@XmlRootElement
public class CreateUserResponse {

    private ApiUser apiUser;
    private OAuth2AccessToken oAuth2AccessToken;

    public CreateUserResponse(){}

    public CreateUserResponse(final ApiUser user, OAuth2AccessToken oAuth2AccessToken) {
        
        notNull(user, "Mandatory argument 'user' missing.");
        notNull(oAuth2AccessToken, "Mandatory argument 'oAuth2AccessToken' missing.");
        this.apiUser = user;
        this.oAuth2AccessToken = oAuth2AccessToken;
    }

    public ApiUser getApiUser() {
        return apiUser;
    }

    public OAuth2AccessToken getOAuth2AccessToken() {
        return oAuth2AccessToken;
    }
}
