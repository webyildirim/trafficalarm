package com.trafficalarm.core.services.exceptions;

/**
 * Created by webyildirim on 6/28/14.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException() {
    }
}
