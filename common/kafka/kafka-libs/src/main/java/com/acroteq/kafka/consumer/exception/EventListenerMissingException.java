package com.acroteq.kafka.consumer.exception;

import com.acroteq.domain.exception.DomainException;

public class EventListenerMissingException extends DomainException {

  private static final String I18N_CODE = "problem.event.listener.missing";
  private static final String MESSAGE = "No event listener was given for the message type %s";

  private final String messageType;

  public EventListenerMissingException(final String messageType) {
    super(String.format(MESSAGE, messageType));

    this.messageType = messageType;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ messageType };
  }
}
