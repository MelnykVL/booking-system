package com.testtask.booking_system.repository;

import com.testtask.booking_system.entity.EventLog;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLogRepository extends JpaRepository<EventLog, UUID> {
}
