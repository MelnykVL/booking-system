package com.testtask.booking_system.dto.notification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingNotificationDto {

  private long userId;
  private long unitId;
  private long bookingId;
  private String content;
}
