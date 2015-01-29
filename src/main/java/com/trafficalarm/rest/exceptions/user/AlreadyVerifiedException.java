package com.trafficalarm.rest.exceptions.user;

import com.trafficalarm.rest.exceptions.BaseWebApplicationException;


/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class AlreadyVerifiedException extends BaseWebApplicationException {

    public AlreadyVerifiedException() {
        super(409, "Already verified", "The token has already been verified");
    }
}
