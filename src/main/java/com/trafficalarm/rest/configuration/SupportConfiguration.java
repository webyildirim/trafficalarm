package com.trafficalarm.rest.configuration;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.trafficalarm.rest.mvc.HealthCheckResource;
import com.trafficalarm.rest.mvc.resource.GenericExceptionMapper;

@Configuration
public class SupportConfiguration {

    @Bean
    public Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean
    @Scope(value = "singleton")
    public GenericExceptionMapper genericExceptionMapper() {
        return new GenericExceptionMapper();
    }

    @Bean
    public HealthCheckResource healthCheckResource() {
        return new HealthCheckResource();
    }

}
