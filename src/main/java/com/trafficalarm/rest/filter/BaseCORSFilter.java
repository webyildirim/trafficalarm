package com.trafficalarm.rest.filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.structure.util.StringUtility;

/**
 * Created by iainporter on 07/08/2014.
 */
public class BaseCORSFilter {

    private static final Set<String> EMPTY = new HashSet<String>();

    private Set<String> parseAllowedOrigins(String allowedOriginsString) {
        if(!StringUtility.isNullOrEmpty(allowedOriginsString)) {
            return new HashSet<String>(Arrays.asList(allowedOriginsString.split(",")));
        } else {
            return EMPTY;
        }

    }

    public Set<String> getAllowedOrigins(String allowedOriginsString) {
        return parseAllowedOrigins(allowedOriginsString);
    }
}
