package com.testtask.booking_system.view;

import java.time.LocalDate;

public interface ExpiredBookingView {

  Long getId();
  Long getUnitId();
  Long getUserId();
  LocalDate getCheckInOn();
  LocalDate getCheckOutOn();
}
