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


        UserDetails userDetails = getUserDetailsService().loadUserByUsername(username);
        String encodedPassword = userDetails.getPassword();

        return super.authenticate(authentication);
    }
}
