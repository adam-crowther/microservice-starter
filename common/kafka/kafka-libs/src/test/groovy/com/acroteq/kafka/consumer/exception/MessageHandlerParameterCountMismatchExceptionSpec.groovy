package com.acroteq.kafka.consumer.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class MessageHandlerParameterCountMismatchExceptionSpec extends Specification {

  static final String PARAMETER_NAME = 'parameter-name'

  def 'message and i18n code should be set'() {
    when:
    def exception = new MessageHandlerParameterCountMismatchException(parameter)

    then:
    exception.userName == 'problem.message.handler.parameter.count.mismatch'
    exception.parameters == [parameter] as String[]
    exception.message == message
    exception.cause == null

    where:
    parameter      || message
    PARAMETER_NAME || 'Mismatch between the number of messages and parameter-names, which should be the same'
    null           || 'Mismatch between the number of messages and nulls, which should be the same'
  }
}
