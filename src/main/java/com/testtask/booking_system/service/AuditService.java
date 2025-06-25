package com.testtask.booking_system.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.booking_system.entity.EventLog;
import com.testtask.booking_system.exception.ObjectToJsonProcessingException;
import com.testtask.booking_system.repository.AuditRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuditService {

  private final AuditRepository auditRepository;
  private final ObjectMapper objectMapper;

  public void log(Class<?> entity, String action, Object payload) {
    this.log(entity, null, action, payload);
  }

  public void log(Class<?> entity, Long entityId, String action, Object payload) {
    EventLog eventLog = createEventLog(entity, entityId, action, payload);
    auditRepository.save(eventLog);
  }

  public void logBatch(Class<?> entity, String action, List<Object> payloads) {
    this.logBatch(entity, null, action, payloads);
  }

  public void logBatch(Class<?> entity, Long entityId, String action, List<Object> payloads) {
    List<EventLog> eventLogs =
        payloads.stream()
                .map(p -> createEventLog(entity, entityId, action, p))
                .toList();
    auditRepository.saveAll(eventLogs);
  }

  private EventLog createEventLog(Class<?> entity, Long entityId, String action, Object payload) {
    EventLog eventLog = new EventLog();
    eventLog.setEntity(entity.getSimpleName());
    eventLog.setEntityId(entityId);
    eventLog.setAction(action);
    try {
      eventLog.setPayload(objectMapper.writeValueAsString(payload));
    } catch (JsonProcessingException ex) {
      throw new ObjectToJsonProcessingException(ex);
    }
    return eventLog;
  }
}
