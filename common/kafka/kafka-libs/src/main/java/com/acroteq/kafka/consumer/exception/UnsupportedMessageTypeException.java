package com.acroteq.kafka.consumer.exception;

import com.acroteq.domain.exception.DomainException;

public class UnsupportedMessageTypeException extends DomainException {

  private static final String I18N_CODE = "problem.unsupported.message.type";
  private static final String MESSAGE = "The message type %s is not supported";

  private final String messageType;

  public UnsupportedMessageTypeException(final String messageType) {
    super(String.format(MESSAGE, messageType));

    this.messageType = messageType;
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ messageType };
  }
}
