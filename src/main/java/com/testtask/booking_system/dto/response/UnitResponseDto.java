package com.testtask.booking_system.dto.response;

import com.testtask.booking_system.enums.AccommodationType;
import java.math.BigDecimal;
import java.time.Instant;

public record UnitResponseDto(
    Long id,
    Long ownerId,
    Integer numberOfRooms,
    Integer floor,
    AccommodationType type,
    BigDecimal pricePerNight,
    String description,
    Instant createdAt
) {
}
