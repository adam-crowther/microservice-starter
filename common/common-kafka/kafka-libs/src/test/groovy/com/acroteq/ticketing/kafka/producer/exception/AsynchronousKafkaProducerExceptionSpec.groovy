package com.acroteq.ticketing.kafka.producer.exception


import spock.lang.Specification

class AsynchronousKafkaProducerExceptionSpec extends Specification {

  RuntimeException cause = Mock(RuntimeException)

  def "message and i18n code should be set"() {
    when:
      def exception = new AsynchronousKafkaProducerException(cause)

    then:
      exception.getCode() == "problem.asynchronous.error.in.kafka.producer"
      exception.getParameters() == [] as String[]
      exception.getMessage() == "Asynchronous exception while sending a message to Kafka"
      exception.getCause() == cause
  }
}
