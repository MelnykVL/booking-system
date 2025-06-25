package com.testtask.booking_system.exception;

public class ObjectToJsonProcessingException extends RuntimeException {

  public ObjectToJsonProcessingException(Exception ex) {
    super(ex);
  }
}
