package com.tech.controller;

import com.tech.entity.Notification;
import com.tech.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // GET /notifications
    // Retrieves notifications for the logged-in user.
    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(Principal principal) {
        List<Notification> notifications = notificationService.getNotificationsForUser(principal.getName());
        return ResponseEntity.ok(notifications);
    }
}