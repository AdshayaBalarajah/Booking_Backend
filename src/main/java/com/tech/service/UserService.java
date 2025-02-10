package com.tech.service;

import com.tech.entity.User;
import com.tech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Method to retrieve a user by email
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    // Method to create a new user
    public User createUser(String fullName, String email, String password) {
        // Validate that fullName is not empty or null
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new RuntimeException("Full name cannot be null or empty");
        }

        // Create a new User object and set its properties
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        // Save the user to the repository and return it
        return userRepository.save(user);
    }
}
