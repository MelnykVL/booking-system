package com.testtask.booking_system;

public class UserEmailAlreadyExistsException extends RuntimeException {

  public UserEmailAlreadyExistsException(String userEmail) {
    super(String.format("User with email '%s' already exists", userEmail));
  }
}
