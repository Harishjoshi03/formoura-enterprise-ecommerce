package com.formoura.notification.mapper;

import com.formoura.notification.dto.request.NotificationRequest;
import com.formoura.notification.dto.response.NotificationResponse;
import com.formoura.notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationRequest request);

    NotificationResponse toResponse(Notification notification);

}