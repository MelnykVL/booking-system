package com.testtask.booking_system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PricingUtils {

  private static final int MONEY_SCALE = 2;
  private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

  public BigDecimal applyMarkup(BigDecimal price, BigDecimal markupPercent) {
    return price.multiply(markupFactor(markupPercent))
        .setScale(MONEY_SCALE, ROUNDING);
  }

  public BigDecimal countPriceForPeriod(LocalDate checkInOn, LocalDate checkOutOn, BigDecimal price) {
    long nights = ChronoUnit.DAYS.between(checkInOn, checkOutOn);

    return price.multiply(BigDecimal.valueOf(nights));
  }

  private BigDecimal markupFactor(BigDecimal markup) {
    return BigDecimal.ONE.add(
        markup.divide(BigDecimal.valueOf(100)));
  }
}
