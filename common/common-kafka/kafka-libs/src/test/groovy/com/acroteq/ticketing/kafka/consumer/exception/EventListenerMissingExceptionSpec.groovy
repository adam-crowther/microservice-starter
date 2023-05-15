package com.acroteq.ticketing.kafka.consumer.exception

import spock.lang.Specification

class EventListenerMissingExceptionSpec extends Specification {

  static final String MESSAGE_TYPE = "message-type"

  def "message and i18n code should be set"() {
    when:
      def exception = new EventListenerMissingException(parameter)

    then:
      exception.getCode() == "problem.event.listener.missing"
      exception.getParameters() == [parameter] as String[]
      exception.getMessage() == message
      exception.getCause() == null

    where:
      parameter    | message
      MESSAGE_TYPE | "No event listener was given for the message type message-type"
      null         | "No event listener was given for the message type null"
  }
}
