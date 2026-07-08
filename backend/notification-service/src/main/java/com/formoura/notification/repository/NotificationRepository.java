package com.formoura.notification.repository;

import com.formoura.notification.entity.Notification;
import com.formoura.notification.entity.NotificationStatus;
import com.formoura.notification.entity.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    // ==========================================
    // User
    // ==========================================

    List<Notification> findByUserId(Long userId);

    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);

    // ==========================================
    // Email
    // ==========================================

    List<Notification> findByEmail(String email);

    List<Notification> findByEmailOrderByCreatedAtDesc(String email);

    // ==========================================
    // Notification Type
    // ==========================================

    List<Notification> findByNotificationType(
            NotificationType notificationType);

    List<Notification> findByNotificationTypeOrderByCreatedAtDesc(
            NotificationType notificationType);

    // ==========================================
    // Status
    // ==========================================

    List<Notification> findByStatus(
            NotificationStatus status);

    List<Notification> findByStatusOrderByCreatedAtDesc(
            NotificationStatus status);

    // ==========================================
    // User + Status
    // ==========================================

    List<Notification> findByUserIdAndStatus(
            Long userId,
            NotificationStatus status);

    // ==========================================
    // User + Type
    // ==========================================

    List<Notification> findByUserIdAndNotificationType(
            Long userId,
            NotificationType notificationType);

    // ==========================================
    // Email + Status
    // ==========================================

    List<Notification> findByEmailAndStatus(
            String email,
            NotificationStatus status);

    // ==========================================
    // Date Range
    // ==========================================

    List<Notification> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end);

    // ==========================================
    // Exists
    // ==========================================

    boolean existsByEmail(String email);

}