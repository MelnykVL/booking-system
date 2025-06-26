package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.request.PaymentCreateDto;
import com.testtask.booking_system.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/{userId}/units/{unitId}/bookings/{bookingId}")
@RequiredArgsConstructor
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/payment")
  public ResponseEntity<String> payForBooking(@PathVariable Long userId, @PathVariable Long unitId,
      @PathVariable Long bookingId, @RequestBody PaymentCreateDto paymentCreateDto) {
    return paymentService.payForBooking(userId, unitId, bookingId, paymentCreateDto);
  }
}
