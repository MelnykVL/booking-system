package com.testtask.booking_system.props;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "booking-system.pricing")
@Validated
public record PricingProps(
    @DecimalMin("0") @DecimalMax("100") BigDecimal markupPercent
) {}
