package com.tech.controller;

import com.tech.dto.AuthResponse;
import com.tech.entity.ApiResponse;
import com.tech.request.LoginRequest;
import com.tech.request.RegisterRequest;
import com.tech.request.ResetPasswordRequest;
import com.tech.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    // POST /auth/register-admin
    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest registerRequest) {
        AuthResponse response = authService.registerAdmin(registerRequest);
        return ResponseEntity.ok(response);
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        // Return JWT token and user details on successful authentication.
        return ResponseEntity.ok(authService.login(loginRequest));
    }


    // POST /auth/refresh-token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

    // POST /auth/forgot-password
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestParam String email) {
        ApiResponse response = authService.initiateForgotPassword(email);
        return ResponseEntity.ok(response);
    }

    // POST /auth/reset-password
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        ApiResponse response = authService.resetPassword(resetPasswordRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("test")
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("Secured end point.");
    }

}