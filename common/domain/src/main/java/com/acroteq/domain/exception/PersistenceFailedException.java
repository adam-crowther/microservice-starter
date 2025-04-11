package com.acroteq.domain.exception;

public abstract class PersistenceFailedException extends DomainException {

  public PersistenceFailedException(final String message) {
    super(message);
  }

  public PersistenceFailedException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
