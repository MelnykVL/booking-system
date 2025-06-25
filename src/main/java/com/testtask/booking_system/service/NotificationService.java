package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.notification.BookingNotificationDto;

public interface NotificationService {

  void sendNotification(BookingNotificationDto bookingNotificationDto);
}
