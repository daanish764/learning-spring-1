package com.example.learningspring1.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class MyCustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        // Log password and encoded version for debugging
        System.out.println("Authenticating user: " + username);
        System.out.println("Password entered: " + password);

        UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);
        String encodedPassword = userDetails.getPassword();
        System.out.println("Stored password: " + encodedPassword);

        return super.authenticate(authentication);
    }
}
