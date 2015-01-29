package com.trafficalarm.rest.api.user;

import static org.springframework.util.Assert.notNull;

import java.security.Principal;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;

import com.trafficalarm.core.model.entities.Account;

@XmlRootElement
public class ApiUser implements Principal {

    @Email
    @NotNull
    private String emailAddress;

    private String firstName;
    private String lastName;
    private String id;

    public ApiUser() {
    }

    public ApiUser(Account user) {
        this.emailAddress = user.getName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        notNull(emailAddress, "Mandatory argument 'emailAddress missing.'");
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        notNull(id, "Mandatory argument 'id missing.'");
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(final String firstName) {
        notNull(firstName, "Mandatory argument 'firstName missing.'");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        notNull(lastName, "Mandatory argument 'lastName missing.'");
        this.lastName = lastName;
    }

    @Override
    public String getName() {
        return this.getEmailAddress();
    }
}