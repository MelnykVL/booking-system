package com.testtask.booking_system.exception;

public class UserEmailAlreadyExistsException extends RuntimeException {

  public UserEmailAlreadyExistsException(String userEmail) {
    super(String.format("User with email '%s' already exists", userEmail));
  }
}
