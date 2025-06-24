package com.testtask.booking_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testtask.booking_system.enums.AccommodationType;
import java.math.BigDecimal;
import java.time.Instant;

public record UnitResponseDto(Long id,
                              Long ownerId,
                              Integer numberOfRooms,
                              Integer floor,
                              AccommodationType type,
                              BigDecimal pricePerNight,
                              @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC") Instant createdAt) {
}
