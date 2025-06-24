package com.testtask.booking_system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MarkupUtils {

  public BigDecimal markupFactor(BigDecimal markup) {
    return BigDecimal.ONE.add(
        markup.divide(BigDecimal.valueOf(100)));
  }

  public BigDecimal applyMarkup(BigDecimal price, BigDecimal markupFactor) {
    return price.multiply(markupFactor)
        .setScale(2, RoundingMode.HALF_UP);
  }
}
