package com.testtask.booking_system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.testtask.booking_system.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record BookingResponseDto(
    Long id,
    @JsonInclude(JsonInclude.Include.NON_NULL) UnitResponseDto unitResponseDto,
    @JsonInclude(JsonInclude.Include.NON_NULL) UserResponseDto userResponseDto,
    LocalDate checkInOn,
    LocalDate checkOutOn,
    BigDecimal totalPrice,
    BookingStatus status,
    Instant expiresAt,
    Instant createdAt
) {}
