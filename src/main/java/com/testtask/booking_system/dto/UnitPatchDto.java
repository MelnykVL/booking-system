package com.testtask.booking_system.dto;

import jakarta.validation.constraints.Positive;

public record UnitPatchDto(
    @Positive(message = "'numberOfRooms' must be greater than 0.") Integer numberOfRooms,
    @Positive(message = "'pricePerNight' must be greater than 0.") Integer pricePerNight
) {}
