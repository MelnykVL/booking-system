package com.testtask.booking_system.exception;

import com.testtask.booking_system.enums.BookingStatus;

public class PaymentNotAllowedException extends RuntimeException {

  public PaymentNotAllowedException() {
    super(String.format("Payment not allowed. Booking must be in '%s' status.", BookingStatus.RESERVED));
  }
}


