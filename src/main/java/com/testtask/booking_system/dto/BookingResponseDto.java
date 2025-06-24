package com.testtask.booking_system.dto;

import com.testtask.booking_system.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record BookingResponseDto(
    Long id,
    UnitResponseDto unitResponseDto,
    UserResponseDto userResponseDto,
    LocalDate checkInOn,
    LocalDate checkOutOn,
    BigDecimal totalCost,
    BookingStatus status,
    Instant expiresAt,
    Instant createdAt
) {}
