package com.example.learningspring1.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {


    @PostMapping("/login")
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        // Obtain the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        // Get the session from the request
        HttpSession session = request.getSession();

        // Create a response map
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("username", authentication.getName());
        responseMap.put("authorities", authentication.getAuthorities());
        responseMap.put("sessionId", session.getId());

        return responseMap;
    }

    @GetMapping("/current-session")
    public Map<String, Object> getCurrentSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get the current session, or null if no session exists
        if (session == null) {
            throw new RuntimeException("No session exists for the current user");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("creationTime", session.getCreationTime());
        response.put("lastAccessedTime", session.getLastAccessedTime());

        return response;
    }

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate the session
//        HttpSession session = request.getSession(false); // Get the current session if it exists
//        if (session != null) {
//            session.invalidate();
//        }

        // Use SecurityContextLogoutHandler to handle session invalidation properly
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());


        // Clear the SecurityContext
//        SecurityContextHolder.clearContext();

        // Return a response indicating successful logout
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "User logged out successfully");
        return ResponseEntity.ok(responseMap);
    }

}
