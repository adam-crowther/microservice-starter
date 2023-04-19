package com.acroteq.ticketing.kafka.producer.exception;

public class KafkaProducerException extends RuntimeException {

  public KafkaProducerException(final String message) {
    super(message);
  }

  public KafkaProducerException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
