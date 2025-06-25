package com.testtask.booking_system.props;

import jakarta.validation.constraints.Positive;
import java.time.Duration;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "booking-system.booking")
@Validated
public record BookingProps(
    @DefaultValue("15m") @DurationMin(minutes = 5) Duration expirationTime,
    @DefaultValue("365") @Positive Integer maxBookingTerm
) {}
