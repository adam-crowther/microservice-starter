package com.acroteq.ticketing.domain.event

import spock.lang.Specification

class EmptyEventSpec extends Specification {

  def "toString returns the expected string"() {
    when:
      def event = EmptyEvent.INSTANCE.toString()

    then:
      event == "EmptyEvent()"
  }
}
