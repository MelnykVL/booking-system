package com.testtask.booking_system.exception;

import com.testtask.booking_system.enums.BookingStatus;

public class BookingCancellationNotAllowedException extends RuntimeException {

  public BookingCancellationNotAllowedException(BookingStatus status) {
    super(String.format("Booking must be in '%s' status. It's currently in '%s' status.", BookingStatus.RESERVED,
        status.name()));
  }
}
