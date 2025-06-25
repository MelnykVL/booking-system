package com.testtask.booking_system.config;

import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.exception.UnitNotBookedByUserException;
import com.testtask.booking_system.exception.UserEmailAlreadyExistsException;
import com.testtask.booking_system.exception.UserNotOwnerBookingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

  @ExceptionHandler(UserEmailAlreadyExistsException.class)
  public ResponseEntity<String> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ResourceNotFountException.class)
  public ResponseEntity<String> handleResourceNotFountException(ResourceNotFountException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(UserNotOwnerBookingException.class)
  public ResponseEntity<String> handleUserNotOwnerBookingException(UserNotOwnerBookingException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnitNotBookedByUserException.class)
  public ResponseEntity<String> handleUnitNotBookedByUserException(UnitNotBookedByUserException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }
}
