package com.acroteq.food.ordering.system.domain.exception;

public abstract class DomainException extends RuntimeException {

  public DomainException(final String message) {
    super(message);
  }
  public DomainException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public abstract String getCode();
  public abstract String[] getParameters();
}
