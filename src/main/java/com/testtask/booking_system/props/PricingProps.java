package com.testtask.booking_system.props;

import com.testtask.booking_system.util.PricingUtils;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "booking-system.pricing")
@Validated
@RequiredArgsConstructor
public class PricingProps {

  @DecimalMin("0") @DecimalMax("100")
  private final BigDecimal markupPercent;

  public BigDecimal getTotalPrice(BigDecimal price) {
    return PricingUtils.applyMarkup(price, markupPercent);
  }
}
