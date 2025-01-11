package com.cinema.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private static final String LOGIN_URL = "/users/login";
    private static final String CREATE_USER_URL = "/users";

    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
            LOGIN_URL,
            CREATE_USER_URL
    };
}