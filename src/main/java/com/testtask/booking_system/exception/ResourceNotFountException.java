package com.testtask.booking_system.exception;

public class ResourceNotFountException extends RuntimeException {

  public ResourceNotFountException(String resourceName, long resourceId) {
    super(String.format("'%s' with id '%d' does not exist.", resourceName, resourceId));
  }
}
