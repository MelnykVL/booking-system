package com.testtask.booking_system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.booking_system.entity.EventLog;
import com.testtask.booking_system.exception.ObjectToJsonProcessingException;
import com.testtask.booking_system.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditService {

  private final AuditRepository auditRepository;
  private final ObjectMapper objectMapper;

  public void log(Class<?> entity, String action) {
    this.log(entity, null, action, null);
  }

  public void log(Class<?> entity, String action, Object payload) {
    this.log(entity, null, action, payload);
  }

  public void log(Class<?> entity, Long entityId, String action, Object payload) {
    EventLog eventLog = new EventLog();
    eventLog.setEntity(entity.getSimpleName());
    eventLog.setEntityId(entityId);
    eventLog.setAction(action);
    try {
      eventLog.setPayload(objectMapper.writeValueAsString(payload));
    } catch (JsonProcessingException ex) {
      throw new ObjectToJsonProcessingException(ex);
    }
    auditRepository.save(eventLog);
  }
}
