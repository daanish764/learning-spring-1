package com.example.learningspring1.controller;

import com.example.learningspring1.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    UserRepository userRepository;

    HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public String index() {
        return "Hello World by Daanish";
    }


}
