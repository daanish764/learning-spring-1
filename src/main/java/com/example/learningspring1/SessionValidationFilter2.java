package com.example.learningspring1;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Base64;

//@WebFilter("/*") // Intercept all requests; you can customize the URL pattern as needed
//@Component
// we are no longer using traditional sessions
public class SessionValidationFilter2 implements Filter {

    private final JdbcTemplate jdbcTemplate;

    public SessionValidationFilter2(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;


            // Exclude the login endpoint from session validation
            String requestURI = httpRequest.getRequestURI();
            if ("/auth/login".equals(requestURI) || "/auth/loginJ".equals(requestURI)) {
                chain.doFilter(request, response);
                return;
            }

            // Validate session for other endpoints
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("SESSION".equals(cookie.getName())) {
                        String sessionId = decodeSessionId(cookie.getValue());

                        if (!isSessionValid(sessionId)) {
                            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Set 401 status
                            httpResponse.getWriter().write("Invalid or expired session");
                            return; // Stop further processing
                        }

                        // If session is valid, continue the filter chain
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }

            // If no valid session is found, reject the request
//            throw new ServletException("No session found. User is not logged in.");
        }

        chain.doFilter(request, response);
    }

    private String decodeSessionId(String encodedSessionId) {
        try {
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedSessionId);
            return new String(decodedBytes);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to decode session ID", e);
        }
    }

    private boolean isSessionValid(String sessionId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM SPRING_SESSION WHERE SESSION_ID = ?",
                Integer.class,
                sessionId
        );

        return count != null && count > 0;
    }
}