package com.testtask.booking_system.dto;

import jakarta.validation.constraints.Positive;

public record UnitPatchDto(
    @Positive Integer numberOfRooms,
    @Positive Integer pricePerNight
) {}
