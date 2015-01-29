package com.trafficalarm.core.services.exceptions;

/**
 * Created by webyildirim on 6/28/14.
 */
public class EntityNotFoundException extends RuntimeException {
	private String entityId;

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}


    public EntityNotFoundException() {
    }
    
	public EntityNotFoundException(String entityId, String message, Throwable cause) {
        super(message, cause);
		this.entityId=entityId;
    }

	public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }
}
