package com.testtask.booking_system.exception;

public class MaxBookingTermException extends RuntimeException {

  public MaxBookingTermException(int maxBookingTerm) {
    super(String.format("Booking cannot exceed %d days", maxBookingTerm));
  }
}
