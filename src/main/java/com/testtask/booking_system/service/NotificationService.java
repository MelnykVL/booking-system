package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.notification.BookingNotifiactionDto;

public interface NotificationService {

  void sendNotification(BookingNotifiactionDto bookingNotifiactionDto);
}
