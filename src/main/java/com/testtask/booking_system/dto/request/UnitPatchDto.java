package com.testtask.booking_system.dto.request;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UnitPatchDto(
    @Positive(message = "'numberOfRooms' must be greater than 0.") Integer numberOfRooms,
    @Positive(message = "'pricePerNight' must be greater than 0.") BigDecimal pricePerNight,
    String description
) {}
