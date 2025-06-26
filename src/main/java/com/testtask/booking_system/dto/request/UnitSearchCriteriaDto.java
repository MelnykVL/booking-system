package com.testtask.booking_system.dto.request;

import com.testtask.booking_system.enums.AccommodationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record UnitSearchCriteriaDto(
    @Positive(message = "'numberOfRooms' must be greater than 0.") Integer numberOfRooms,
    @Min(value = 0, message = "'floor' cannot be less than 0.") Integer floor,
    AccommodationType type,
    @Positive(message = "'minTotalPrice' must be greater than 0.00.") BigDecimal minTotalPrice,
    @Positive(message = "'maxTotalPrice' must be greater than 0.00.") BigDecimal maxTotalPrice,
    @NotNull(message = "'availableFrom' cannot be null.") @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent LocalDate availableFrom,
    @NotNull(message = "'availableTo' cannot be null.") @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future LocalDate availableTo
) {
}
