package com.trafficalarm.core.model.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

import com.structure.BaseEntity;

/**
 * A token that gives the user permission to carry out a specific task once within a determined time period.
 * An example would be a Lost Password token. The user receives the token embedded in a link.
 * The user sends the token back to the server by clicking the link and the action is processed
 *
 * @version 1.0
 * @author: Iain Porter
 * @since 14/05/2013
 */
public class VerificationToken extends BaseEntity {

    private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours

    private final String token;

    private Date expiryDate;

    private VerificationTokenType tokenType;

    private boolean verified;
    
    @ManyToOne
    private Account account;

    public VerificationToken() {
        super();
        this.entityName="VerificationToken";
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
    }

    public VerificationToken(Account user, VerificationTokenType tokenType, int expirationTimeInMinutes) {
        this();
        this.entityName="VerificationToken";
        this.account = user;
        this.tokenType = tokenType;
        this.expiryDate = calculateExpiryDate(expirationTimeInMinutes);
    }

    public VerificationTokenType getTokenType() {
        return tokenType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getToken() {
        return token;
    }

    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	private Date calculateExpiryDate(int expiryTimeInMinutes) {
        DateTime now = new DateTime();
        return now.plusMinutes(expiryTimeInMinutes).toDate();
    }

    public boolean hasExpired() {
        DateTime tokenDate = new DateTime(getExpiryDate());
        return tokenDate.isBeforeNow();
    }

}

