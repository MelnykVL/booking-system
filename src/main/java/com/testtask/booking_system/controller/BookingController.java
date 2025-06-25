package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.request.BookingCreateDto;
import com.testtask.booking_system.dto.response.BookingResponseDto;
import com.testtask.booking_system.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/units/{unitId}/bookings")
@RequiredArgsConstructor
public class BookingController {

  private final BookingService bookingService;

  @PostMapping
  public ResponseEntity<BookingResponseDto> createBooking(@PathVariable Long userId, @PathVariable Long unitId,
      @Valid @RequestBody BookingCreateDto bookingCreateDto) {
    return bookingService.createBooking(userId, unitId, bookingCreateDto);
  }

  @DeleteMapping("/{bookingId}/cancel")
  public ResponseEntity<Void> cancelBooking(@PathVariable Long userId, @PathVariable Long unitId,
      @PathVariable Long bookingId) {
    return bookingService.cancelBooking(userId, unitId, bookingId);
  }
}
