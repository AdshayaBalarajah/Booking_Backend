package com.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;

    // Setter for token
    public void setToken(String token) {
        this.token = token;
    }

    // Setter for user
    public void setUser(User user) {
        this.user = user;
    }

    // Setter for expiryDate using LocalDateTime
    public void setExpiryDate(LocalDateTime localDateTime) {
        this.expiryDate = localDateTime.toInstant(ZoneOffset.UTC);
    }

    public String getToken() {
        return token;
    }

    // Getter for user
    public User getUser() {
        return user;
    }
}