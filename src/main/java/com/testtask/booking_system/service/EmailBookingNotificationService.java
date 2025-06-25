package com.testtask.booking_system.service;

import com.testtask.booking_system.NotificationSender;
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
public class EmailBookingNotificationService implements NotificationService {

  private static final String DEFAULT_MESSAGE_CANCEL_BOOKING = "Your booking '%d' has been cancelled.";

  private final UserService userService;
  private final AuditService auditService;
  private NotificationSender notificationSender;

  @Async
  @TransactionalEventListener
  public void sendNotification(BookingNotificationDto bookingNotificationDto) {
    String toUser = userService.findUser(bookingNotificationDto.getUserId()).getBody().email();
    bookingNotificationDto.setContent(DEFAULT_MESSAGE_CANCEL_BOOKING);
    //notificationSender.send(toUser, DEFAULT_MESSAGE_CANCEL_BOOKING);
    auditService.log(NotificationService.class, EventLogAction.SEND_NOTIFICATION.name(), bookingNotificationDto);
    log.info("EMULATING NOTIFICATION...");
  }
}
