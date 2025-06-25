package com.testtask.booking_system.exception;

public class UnitNotBookedByUserException extends RuntimeException {

  public UnitNotBookedByUserException(long userId, long unitId) {
    super(String.format("User '%d' has not booked unit '%s'.", userId, unitId));
  }
}
