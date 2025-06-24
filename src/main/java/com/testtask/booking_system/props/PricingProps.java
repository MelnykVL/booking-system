package com.testtask.booking_system.props;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "booking-system.pricing")
@Validated
@Getter
@RequiredArgsConstructor
public class PricingProps {

  @DecimalMin("0")
  @DecimalMax("100")
  private final BigDecimal markupPercent;
}
