package com.example.learningspring1.controller;

import com.example.learningspring1.model.User;
import com.example.learningspring1.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.net.ssl.SSLEngineResult;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    UserRepository userRepository;
    AuthenticationManager authenticationManager;

    public UserController(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {

        return new ResponseEntity("Hello World", HttpStatus.OK);

    }

    public record LoginRequest(String username, String password) { }

    @PostMapping(value = "/register")
    public User register(@RequestHeader HttpHeaders headers, @RequestBody @Valid User user) {

        if(headers.containsKey(HttpHeaders.AUTHORIZATION)) {
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            System.out.println(authHeader);

            if(authHeader.startsWith("Basic ")) {
                System.out.println("authorized!");
            }
            else {
                System.out.println("unauthorized");
            }
        }


        return userRepository.save(user);
    }


    @GetMapping("")
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }



}
