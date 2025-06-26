package com.testtask.booking_system.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PaymentCreateDto(
    @NotNull(message = "'amount' cannot be null.")
    @Positive(message = "'amount' must be greater than 0.00.") BigDecimal amount
) {}
