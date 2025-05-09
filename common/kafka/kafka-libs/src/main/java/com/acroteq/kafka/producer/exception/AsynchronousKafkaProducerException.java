package com.acroteq.kafka.producer.exception;

import com.acroteq.domain.exception.DomainException;

public class AsynchronousKafkaProducerException extends DomainException {

  private static final String I18N_CODE = "problem.asynchronous.error.in.kafka.producer";
  private static final String MESSAGE = "Asynchronous exception while sending a message to Kafka";

  public AsynchronousKafkaProducerException(final Throwable cause) {

    super(MESSAGE, cause);
  }

  @Override
  public String getUserName() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
