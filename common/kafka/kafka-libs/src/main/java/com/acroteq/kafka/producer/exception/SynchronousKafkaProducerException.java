package com.acroteq.ticketing.kafka.producer.exception;

import com.acroteq.domain.exception.DomainException;

public class SynchronousKafkaProducerException extends DomainException {

  private static final String I18N_CODE = "problem.synchronous.error.in.kafka.producer";
  private static final String MESSAGE = "Synchronous exception while sending a message to Kafka";

  public SynchronousKafkaProducerException(final Throwable cause) {

    super(MESSAGE, cause);
  }

  @Override
  public String getCode() {
    return I18N_CODE;
  }

  @Override
  public String[] getParameters() {
    return new String[0];
  }
}
