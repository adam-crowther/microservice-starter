package com.acroteq.kafka.consumer.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class EventListenerMissingExceptionSpec extends Specification {

  static final String MESSAGE_TYPE = 'message-type'

  def 'message and i18n code should be set'() {
    when:
    def exception = new EventListenerMissingException(parameter)

    then:
    exception.code == 'problem.event.listener.missing'
    exception.parameters == [parameter] as String[]
    exception.message == message
    exception.cause == null

    where:
    parameter    || message
    MESSAGE_TYPE || 'No event listener was given for the message type message-type'
    null         || 'No event listener was given for the message type null'
  }
}
