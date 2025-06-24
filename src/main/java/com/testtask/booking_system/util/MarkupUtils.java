package com.testtask.booking_system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MarkupUtils {

  public BigDecimal applyMarkup(BigDecimal price, BigDecimal markupPercent) {
    return price.multiply(markupFactor(markupPercent))
        .setScale(2, RoundingMode.HALF_UP);
  }

  public BigDecimal markupFactor(BigDecimal markup) {
    return BigDecimal.ONE.add(
        markup.divide(BigDecimal.valueOf(100)));
  }
}
