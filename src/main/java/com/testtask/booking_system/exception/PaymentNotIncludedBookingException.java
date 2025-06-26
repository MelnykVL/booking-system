package com.testtask.booking_system.exception;

public class PaymentNotIncludedBookingException extends RuntimeException {

  public PaymentNotIncludedBookingException(long paymentId, long bookingId) {
    super(String.format("Payment '%d' is not included booking '%d'.", paymentId, bookingId));
  }
}
