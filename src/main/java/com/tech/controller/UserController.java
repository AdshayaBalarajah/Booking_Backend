package com.tech.controller;

import com.tech.entity.User;
import com.tech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        if (user.getFullName() == null || user.getFullName().isEmpty()) {
            throw new RuntimeException("Full name is required");
        }
        return userRepository.save(user);
    }
}
