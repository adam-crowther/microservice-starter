package com.acroteq.ticketing.kafka.consumer.exception;

import com.acroteq.ticketing.domain.exception.DomainException;

public class MessageToDtoMapperMissingException extends DomainException {

  private static final String I18N_CODE = "problem.message.to.dto.mapper.missing";
  private static final String MESSAGE = "No message to dto mapper was given for the message type %s";

  private final String messageType;

  public MessageToDtoMapperMissingException(final String messageType) {
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
