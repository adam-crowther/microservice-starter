package com.acroteq.domain.exception;

public abstract class NotFoundException extends DomainException {

  public NotFoundException(final String message) {
    super(message);
  }

  public NotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
