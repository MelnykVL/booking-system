package com.testtask.booking_system.dto.request;

import com.testtask.booking_system.enums.AccommodationType;
import java.math.BigDecimal;
import java.time.LocalDate;

public record UnitFilter(
    Integer numberOfRooms,
    Integer floor,
    AccommodationType type,
    BigDecimal minTotalPrice,
    BigDecimal maxTotalPrice,
    LocalDate availableFrom,
    LocalDate availableTo
) {
}
