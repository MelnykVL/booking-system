package com.testtask.booking_system.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.format.annotation.DateTimeFormat;

public record BookingCreateDto(
    @NotNull(message = "'checkInOn' cannot be null.") @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent LocalDate checkInOn,
    @NotNull(message = "'checkOutOn' cannot be null.") @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future LocalDate checkOutOn
) {

  @AssertTrue(message = "The dates cannot be the same.")
  private boolean isDatesMatch() {
    return Objects.nonNull(checkInOn) && Objects.nonNull(checkOutOn) && checkOutOn.isAfter(checkInOn);
  }
}
