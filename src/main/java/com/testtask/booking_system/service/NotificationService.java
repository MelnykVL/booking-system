package com.testtask.booking_system.service;

import com.testtask.booking_system.enums.EventLogAction;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

  private final AuditService auditService;

  public void sendPaymentSuccessNotification(long userId, long unitId, long bookingId, String content) {
    Map<String, Object> payload = new HashMap<>();
    payload.put("userId", userId);
    payload.put("unitId", unitId);
    payload.put("bookingId", bookingId);
    payload.put("content", content);
    auditService.log(NotificationService.class, EventLogAction.SEND_NOTIFICATION.name(), payload);
    log.info("EMULATING NOTIFICATION...");
  }
}
