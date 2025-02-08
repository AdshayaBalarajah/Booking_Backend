package com.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "forgot_password_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate; // Token expiration time

    @Column(nullable = false)
    private boolean used;

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // Getter for user
    public User getUser() {
        return user;
    }
}

