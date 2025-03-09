package com.acroteq.ticketing.kafka.consumer.exception;

import com.acroteq.domain.exception.DomainException;

public class MessageHandlerParameterCountMismatchException extends DomainException {

  private static final String I18N_CODE = "problem.message.handler.parameter.count.mismatch";
  private static final String MESSAGE = "Mismatch between the number of messages and %ss, which should be the same";

  private final String parameterName;

  public MessageHandlerParameterCountMismatchException(final String parameterName) {
    super(String.format(MESSAGE, parameterName));

    this.parameterName = parameterName;
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[]{ parameterName };
  }
}
