package com.testtask.booking_system.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.LocalDate;

public record BookingCreateDto(
    @FutureOrPresent LocalDate checkInOn,
    @Future LocalDate checkOutOn
) {}
