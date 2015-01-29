package com.trafficalarm.rest.exceptions.user;

import com.trafficalarm.rest.exceptions.BaseWebApplicationException;


/**
 * @version 1.0
 * @author: Iain Porter
 * @since 13/05/2013
 */
public class TokenHasExpiredException extends BaseWebApplicationException {

    public TokenHasExpiredException() {
        super(403, "Token has expired", "An attempt was made to load a token that has expired");
    }
}
