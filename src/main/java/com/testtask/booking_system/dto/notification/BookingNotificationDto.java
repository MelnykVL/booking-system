package com.testtask.booking_system.dto.notification;

public record BookingNotificationDto(
    long userId,
    long unitId,
    long bookingId,
    String content
) {
}
