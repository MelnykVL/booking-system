package com.testtask.booking_system.config;

import java.sql.SQLException;
import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String EXCLUSION_CONSTRAINT_ERROR_CODE = "23P01";

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleRuntimeErrors(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<String> handleNoResourceFoundException(NoResourceFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ex.getMessage());
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    String errors = ex.getBindingResult()
        .getAllErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(" "));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(errors);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
    Throwable root = ExceptionUtils.getRootCause(ex);
    if (root instanceof SQLException sqlException && EXCLUSION_CONSTRAINT_ERROR_CODE.equals(
        sqlException.getSQLState())) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body("Unit is already booked for the selected dates.");
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
  }
}
