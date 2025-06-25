package com.testtask.booking_system.props;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "booking-system.booking")
@Validated
public record BookingProps(
    @DefaultValue("15m") @FutureOrPresent Duration expirationTime,
    @DefaultValue("365") @Positive Integer maxBookingTerm
) {}
