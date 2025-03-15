package com.acroteq.exception;

public class MoreThanOneItemInStreamException extends IllegalStateException {

  private static final String MESSAGE = "Expected exactly one item in the stream, found more than one";

  public MoreThanOneItemInStreamException() {
    super(MESSAGE);
  }
}
