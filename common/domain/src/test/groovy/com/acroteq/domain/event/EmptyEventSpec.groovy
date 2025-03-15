package com.acroteq.domain.event

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class EmptyEventSpec extends Specification {

  def 'toString returns the expected string'() {
    when:
    def event = EmptyEvent.INSTANCE.toString()

    then:
    event == 'EmptyEvent()'
  }
}
