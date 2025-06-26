package com.testtask.booking_system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(UserEmailAlreadyExistsException.class)
  public ResponseEntity<String> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

  @ExceptionHandler(ResourceNotFountException.class)
  public ResponseEntity<String> handleResourceNotFountException(ResourceNotFountException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler(UserNotOwnerBookingException.class)
  public ResponseEntity<String> handleUserNotOwnerBookingException(UserNotOwnerBookingException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN)
        .body(ex.getMessage());
  }

  @ExceptionHandler(PaymentNotIncludedBookingException.class)
  public ResponseEntity<String> handlePaymentNotIncludedBookingException(PaymentNotIncludedBookingException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

  @ExceptionHandler(UnitNotBookedByUserException.class)
  public ResponseEntity<String> handleUnitNotBookedByUserException(UnitNotBookedByUserException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler(BookingCancellationNotAllowedException.class)
  public ResponseEntity<String> handleBookingCancellationNotAllowedException(
      BookingCancellationNotAllowedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

  @ExceptionHandler(MaxBookingTermException.class)
  public ResponseEntity<String> handleMaxBookingTermException(MaxBookingTermException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }

  @ExceptionHandler(PaymentNotAllowedException.class)
  public ResponseEntity<String> handlePaymentNotAllowedException(PaymentNotAllowedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ex.getMessage());
  }
}
