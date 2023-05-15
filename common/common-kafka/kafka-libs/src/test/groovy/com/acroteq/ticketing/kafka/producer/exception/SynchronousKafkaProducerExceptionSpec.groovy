package com.acroteq.ticketing.kafka.producer.exception


import spock.lang.Specification

class SynchronousKafkaProducerExceptionSpec extends Specification {

  RuntimeException cause = Mock(RuntimeException)

  def "message and i18n code should be set"() {
    when:
      def exception = new SynchronousKafkaProducerException(cause)

    then:
      exception.getCode() == "problem.synchronous.error.in.kafka.producer"
      exception.getParameters() == [] as String[]
      exception.getMessage() == "Synchronous exception while sending a message to Kafka"
      exception.getCause() == cause
  }
}
