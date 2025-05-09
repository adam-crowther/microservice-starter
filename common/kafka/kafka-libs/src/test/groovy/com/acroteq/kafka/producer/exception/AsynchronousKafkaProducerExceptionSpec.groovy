package com.acroteq.kafka.producer.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class AsynchronousKafkaProducerExceptionSpec extends Specification {

  RuntimeException cause = Mock(RuntimeException)

  def 'message and i18n code should be set'() {
    when:
    def exception = new AsynchronousKafkaProducerException(cause)

    then:
    exception.userName == 'problem.asynchronous.error.in.kafka.producer'
    exception.parameters == [] as String[]
    exception.message == 'Asynchronous exception while sending a message to Kafka'
    exception.cause == cause
  }
}
