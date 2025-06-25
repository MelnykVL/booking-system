package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.notification.BookingNotificationDto;
import com.testtask.booking_system.enums.EventLogAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingNotificationService {

  private static final String DEFAULT_MESSAGE_CANCEL_BOOKING = "Your booking '%d' has been cancelled.";

  private final AuditService auditService;

  @Async
  @TransactionalEventListener
  public void sendNotification(BookingNotificationDto bookingNotificationDto) {
    auditService.log(NotificationService.class, EventLogAction.SEND_NOTIFICATION.name(), bookingNotificationDto);
    log.info("EMULATING NOTIFICATION...");
  }
}
