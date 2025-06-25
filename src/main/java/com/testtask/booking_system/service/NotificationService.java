package com.testtask.booking_system.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

  public void sendPaymentSuccessNotification(long userId, long unitId, long bookingId) {
    log.info("EMULATING NOTIFICATION...");
  }
}
