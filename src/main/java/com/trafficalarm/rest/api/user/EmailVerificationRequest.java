package com.trafficalarm.rest.api.user;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
@XmlRootElement
public class EmailVerificationRequest {

    private String emailAddress;

    public EmailVerificationRequest() {}

    public EmailVerificationRequest(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @NotNull
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
