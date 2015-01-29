package com.trafficalarm.rest.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.trafficalarm.rest.exceptions.BadClientCredentialsException;

/**
 * Created by iainporter on 16/10/2014.
 */
@Component
public class OAuthRestEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Gson gson = new Gson();
        String errorEntity = gson.toJson(new BadClientCredentialsException("Bad client credentials"));
        response.getOutputStream().print(errorEntity);
    }
}
