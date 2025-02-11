package com.tech.service;

import com.tech.entity.*;
import com.tech.repository.ForgotPasswordRepository;
import com.tech.repository.RefreshTokenRepository;
import com.tech.repository.UserRepository;

import com.tech.request.LoginRequest;
import com.tech.request.RegisterRequest;
import com.tech.request.ResetPasswordRequest;
import com.tech.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       ForgotPasswordRepository forgotPasswordRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    public ApiResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new ApiResponse(false, "Email already registered");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));


        user.setRole(UserRole.USER);

        userRepository.save(user);
        return new ApiResponse(true, "Registration successful");
    }

    public Object login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT access token
        String token = jwtUtil.generateToken(user);

        // Generate a refresh token
         RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        Map<String, Object> authResponse = new HashMap<>();
        authResponse.put("token", token);
        authResponse.put("refreshToken", refreshToken.getToken());
        authResponse.put("user", user);

        return authResponse;
    }

    public Object refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        User user = refreshToken.getUser();
        String newToken = jwtUtil.generateToken(user);

        Map<String, Object> response = new HashMap<>();
        response.put("token", newToken);
        return response;
    }

    public ApiResponse initiateForgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setToken(UUID.randomUUID().toString());
        forgotPassword.setUser(user);
        forgotPasswordRepository.save(forgotPassword);

        return new ApiResponse(true, "Password reset link sent to email");
    }

    public ApiResponse resetPassword(ResetPasswordRequest request) {
        ForgotPassword forgotPassword = forgotPasswordRepository.findByToken(request.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid or expired token"));

        User user = forgotPassword.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        forgotPasswordRepository.delete(forgotPassword);

        return new ApiResponse(true, "Password reset successfully");
    }
}
