package com.trafficalarm.rest.api.user;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 24/04/2013
 */
@XmlRootElement
public class PasswordRequest {

    @Length(min=8, max=30)
    @NotNull
    private String password;

    public PasswordRequest() {}

    public PasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
