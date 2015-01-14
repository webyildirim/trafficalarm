package com.trafficalarm.core.services.exceptions;

/**
 * Created by webyildirim on 6/30/14.
 */
public class AccountExistsException extends RuntimeException {
    public AccountExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExistsException(String message) {
        super(message);
    }

    public AccountExistsException() {
        super();
    }
}
