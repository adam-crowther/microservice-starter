package com.acroteq.ticketing.kafka.consumer.exception

import spock.lang.Specification

class MessageHandlerParameterCountMismatchExceptionSpec extends Specification {

  static final String PARAMETER_NAME = "parameter-name"

  def "message and i18n code should be set"() {
    when:
      def exception = new MessageHandlerParameterCountMismatchException(parameter)

    then:
      exception.getCode() == "problem.message.handler.parameter.count.mismatch"
      exception.getParameters() == [parameter] as String[]
      exception.getMessage() == message
      exception.getCause() == null

    where:
      parameter      | message
      PARAMETER_NAME | "Mismatch between the number of messages and parameter-names, which should be the same"
      null           | "Mismatch between the number of messages and nulls, which should be the same"
  }
}
