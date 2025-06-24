package com.testtask.booking_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "event_logs", schema = "booking_system")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventLog {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;

  @Column(nullable = false)
  private String entity;

  @Column(nullable = false)
  private Long entityId;

  @Column(nullable = false)
  private String action;

  @Column(columnDefinition = "jsonb")
  private String payload;

  @CreationTimestamp
  private Instant createdAt;
}
