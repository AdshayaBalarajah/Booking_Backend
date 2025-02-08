package com.tech.repository;

import com.tech.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // Retrieve notifications for a specific user (or admin, based on your implementation)
    List<Notification> findByUserId(Long userId);
}