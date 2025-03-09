package com.acroteq.ticketing.kafka.producer.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class SynchronousKafkaProducerExceptionSpec extends Specification {

  RuntimeException cause = Mock(RuntimeException)

  def 'message and i18n code should be set'() {
    when:
    def exception = new SynchronousKafkaProducerException(cause)

    then:
    exception.code == 'problem.synchronous.error.in.kafka.producer'
    exception.parameters == [] as String[]
    exception.message == 'Synchronous exception while sending a message to Kafka'
    exception.cause == cause
  }
}
