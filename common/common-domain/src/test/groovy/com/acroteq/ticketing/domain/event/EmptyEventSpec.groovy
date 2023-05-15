package com.acroteq.ticketing.domain.event

import spock.lang.Specification

class EmptyEventSpec extends Specification {

  def "toString returns the expected string"() {
    when:
      def string = EmptyEvent.INSTANCE.toString()

    then:
      string == "EmptyEvent()"
  }
}
