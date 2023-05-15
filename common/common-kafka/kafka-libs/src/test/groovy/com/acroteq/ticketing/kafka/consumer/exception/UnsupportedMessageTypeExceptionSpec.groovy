package com.acroteq.ticketing.kafka.consumer.exception

import spock.lang.Specification

class UnsupportedMessageTypeExceptionSpec extends Specification {

  static final String MESSAGE_TYPE = "message-type"

  def "message and i18n code should be set"() {
    when:
      def exception = new UnsupportedMessageTypeException(parameter)

    then:
      exception.getCode() == "problem.unsupported.message.type"
      exception.getParameters() == [parameter] as String[]
      exception.getMessage() == message
      exception.getCause() == null

    where:
      parameter    | message
      MESSAGE_TYPE | "The message type message-type is not supported"
      null         | "The message type null is not supported"
  }
}
