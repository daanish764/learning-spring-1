package com.example.learningspring1.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    public CustomAuthenticationEntryPoint() {
        this.setRealmName("Yolo");
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Suppress the 'WWW-Authenticate' header
        response.setHeader("WWW-Authenticate", "FormBased");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    }
}