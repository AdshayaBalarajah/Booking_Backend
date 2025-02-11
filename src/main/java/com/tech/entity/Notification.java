package com.tech.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Notification receiver (User)

//    @ManyToOne
//    @JoinColumn(name = "admin_id")
//    private Admin admin; // Optional: Admin notifications

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isRead = false; // Default value to 'false'

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Automatically set to the current time when created

    // Constructor for creating notification with essential fields
    public Notification(User user, String message) {
        this.user = user;
        this.message = message;
        this.isRead = false; // Default value for new notifications
        this.createdAt = LocalDateTime.now(); // Current time
    }
}
