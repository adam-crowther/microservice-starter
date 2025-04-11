package com.acroteq.domain.exception;

public abstract class DomainValidationException extends DomainException {

  public DomainValidationException(final String message) {
    super(message);
  }

  public DomainValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
