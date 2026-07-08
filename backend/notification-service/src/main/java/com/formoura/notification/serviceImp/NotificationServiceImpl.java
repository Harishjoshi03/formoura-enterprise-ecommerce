package com.formoura.notification.serviceImp;

import com.formoura.exception.exception.BusinessException;
import com.formoura.notification.dto.request.NotificationRequest;
import com.formoura.notification.dto.response.NotificationResponse;
import com.formoura.notification.entity.Notification;
import com.formoura.notification.entity.NotificationStatus;
import com.formoura.notification.entity.NotificationType;
import com.formoura.notification.mapper.NotificationMapper;
import com.formoura.notification.repository.NotificationRepository;
import com.formoura.notification.service.NotificationService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;

    private final NotificationMapper mapper;

    private final JavaMailSender mailSender;

    // ============================================
    // Send Notification
    // ============================================

    @Override
    @CircuitBreaker(
            name = "notificationService",
            fallbackMethod = "notificationFallback")

    @Retry(name = "notificationService")

    @RateLimiter(name = "notificationService")

    @Bulkhead(name = "notificationService")
    public NotificationResponse sendNotification(
            NotificationRequest request) {

        Notification notification =
                mapper.toEntity(request);

        try {

            SimpleMailMessage mail =
                    new SimpleMailMessage();

            mail.setTo(request.getEmail());

            mail.setSubject(request.getSubject());

            mail.setText(request.getMessage());

            mailSender.send(mail);

            notification.setStatus(
                    NotificationStatus.SENT);

            notification.setSentAt(
                    LocalDateTime.now());

        } catch (Exception ex) {

            notification.setStatus(
                    NotificationStatus.FAILED);

        }

        Notification saved =
                repository.save(notification);

        return mapper.toResponse(saved);

    }

    // ============================================
    // Get Notification By Id
    // ============================================

    @Override
    public NotificationResponse getNotificationById(
            Long id) {

        Notification notification =
                repository.findById(id)

                        .orElseThrow(() ->
                                new BusinessException(
                                        "Notification Not Found"));

        return mapper.toResponse(notification);

    }

    // ============================================
    // Get All Notifications
    // ============================================

    @Override
    public List<NotificationResponse>
    getAllNotifications() {

        return repository.findAll()

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    // ============================================
    // Get Notifications By User
    // ============================================

    @Override
    public List<NotificationResponse>
    getNotificationsByUserId(Long userId) {

        return repository
                .findByUserIdOrderByCreatedAtDesc(userId)

                .stream()

                .map(mapper::toResponse)

                .toList();

    }

    // ============================================
    // Get Notifications By Email
    // ============================================

    @Override
    public List<NotificationResponse> getNotificationsByEmail(
            String email) {

        return repository
                .findByEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ============================================
    // Get Notifications By Status
    // ============================================

    @Override
    public List<NotificationResponse> getNotificationsByStatus(
            NotificationStatus status) {

        return repository
                .findByStatusOrderByCreatedAtDesc(status)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ============================================
    // Get Notifications By Type
    // ============================================

    @Override
    public List<NotificationResponse> getNotificationsByType(
            NotificationType type) {

        return repository
                .findByNotificationTypeOrderByCreatedAtDesc(type)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ============================================
    // Get Notifications Between Dates
    // ============================================

    @Override
    public List<NotificationResponse> getNotificationsBetweenDates(
            LocalDateTime start,
            LocalDateTime end) {

        return repository
                .findByCreatedAtBetween(start, end)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ============================================
    // Update Notification Status
    // ============================================

    @Override
    public NotificationResponse updateNotificationStatus(
            Long id,
            NotificationStatus status) {

        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Notification Not Found"));

        notification.setStatus(status);

        if (status == NotificationStatus.SENT) {
            notification.setSentAt(LocalDateTime.now());
        }

        Notification updatedNotification =
                repository.save(notification);

        return mapper.toResponse(updatedNotification);
    }

    // ============================================
    // Delete Notification
    // ============================================

    @Override
    public void deleteNotification(Long id) {

        Notification notification = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Notification Not Found"));

        repository.delete(notification);
    }

}