package com.testtask.booking_system.dto;

import com.testtask.booking_system.enums.AccommodationType;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record UnitCreateDto(@NotNull Long ownerId,
                            @NotNull Integer numberOfRooms,
                            @NotNull Integer floor,
                            @NotNull AccommodationType type,
                            @NotNull BigDecimal pricePerNight) {}
