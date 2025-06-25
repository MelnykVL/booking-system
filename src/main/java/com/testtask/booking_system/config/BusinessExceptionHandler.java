package com.testtask.booking_system.config;

import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.exception.UserEmailAlreadyExistsException;
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
}
