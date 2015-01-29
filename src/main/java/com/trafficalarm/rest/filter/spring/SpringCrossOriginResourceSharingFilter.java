package com.trafficalarm.rest.filter.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.trafficalarm.rest.filter.BaseCORSFilter;

/**
 * Created by iainporter on 26/07/2014.
 */
@Component
public class SpringCrossOriginResourceSharingFilter extends BaseCORSFilter implements Filter {

    @Value("${cors.allowed.origins}")
    String allowedOriginsString;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if(req instanceof HttpServletRequest) {
            if (((HttpServletRequest) req).getHeader("Origin") != null) {
                String origin = ((HttpServletRequest) req).getHeader("Origin");
                if (getAllowedOrigins(allowedOriginsString).contains(origin)) {
                    HttpServletResponse response = (HttpServletResponse) res;
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    response.setHeader("Access-Control-Allow-Methods",  "GET, POST, PUT, DELETE, OPTIONS");
                    response.setHeader("Access-Control-Max-Age", "3600");
                    response.setHeader("Access-Control-Allow-Headers", "x-requested-with, Authorization, Content-Type");
                }
            }
        }
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}