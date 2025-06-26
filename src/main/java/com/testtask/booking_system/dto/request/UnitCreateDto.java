package com.testtask.booking_system.dto.request;

import com.testtask.booking_system.enums.AccommodationType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record UnitCreateDto(
    @NotNull(message = "'numberOfRooms' cannot be null.")
    @Positive(message = "'numberOfRooms' must be greater than 0.") Integer numberOfRooms,
    @NotNull(message = "'floor' cannot be null.")
    @Min(value = 0, message = "'floor' cannot be less than 0.") Integer floor,
    @NotNull(message = "'type' cannot be blank.") AccommodationType type,
    @NotNull(message = "'pricePerNight' cannot be null.")
    @Positive(message = "'pricePerNight' must be greater than 0.00.") BigDecimal pricePerNight,
    String description
) {}
