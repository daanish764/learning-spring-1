package com.example.learningspring1.controller;

import com.example.learningspring1.config.jwtconfig.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


//    @PostMapping("/login")
//    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
//        // Obtain the current authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("User not authenticated");
//        }
//
//        // Get the session from the request
//        HttpSession session = request.getSession();
//
//        // Create a response map
//        Map<String, Object> responseMap = new HashMap<>();
//        responseMap.put("username", authentication.getName());
//        responseMap.put("authorities", authentication.getAuthorities());
//        responseMap.put("sessionId", session.getId());
//
//        return responseMap;
//    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(authRequest.getUsername());
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

//    @GetMapping("/current-session")
//    public Map<String, Object> getCurrentSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false); // Get the current session, or null if no session exists
//        if (session == null) {
//            throw new RuntimeException("No session exists for the current user");
//        }
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("sessionId", session.getId());
//        response.put("creationTime", session.getCreationTime());
//        response.put("lastAccessedTime", session.getLastAccessedTime());
//
//        return response;
//    }

    // SecSecurityConfig has a logouts and will handle logging user out
//    @GetMapping("/logout")
//    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
//
//        // Use SecurityContextLogoutHandler to handle session invalidation properly
//        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
//        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//
//
//        // Return a response indicating successful logout
//        Map<String, String> responseMap = new HashMap<>();
//        responseMap.put("message", "User logged out successfully");
//        return ResponseEntity.ok(responseMap);
//    }

}
