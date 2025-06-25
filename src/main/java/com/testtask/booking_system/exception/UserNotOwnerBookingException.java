package com.testtask.booking_system.exception;

public class UserNotOwnerBookingException extends RuntimeException {

  public UserNotOwnerBookingException(long userId, long bookingId) {
    super(String.format("User '%d' is not the owner of booking '%s'.", userId, bookingId));
  }
}
