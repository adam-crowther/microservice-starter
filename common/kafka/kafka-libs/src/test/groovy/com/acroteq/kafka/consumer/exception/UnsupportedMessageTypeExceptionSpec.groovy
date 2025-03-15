package com.acroteq.kafka.consumer.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class UnsupportedMessageTypeExceptionSpec extends Specification {

  static final String MESSAGE_TYPE = 'message-type'

  def 'message and i18n code should be set'() {
    when:
    def exception = new UnsupportedMessageTypeException(parameter)

    then:
    exception.code == 'problem.unsupported.message.type'
    exception.parameters == [parameter] as String[]
    exception.message == message
    exception.cause == null

    where:
    parameter    || message
    MESSAGE_TYPE || 'The message type message-type is not supported'
    null         || 'The message type null is not supported'
  }
}
