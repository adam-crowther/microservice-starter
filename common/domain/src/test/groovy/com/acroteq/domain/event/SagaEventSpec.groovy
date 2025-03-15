package com.acroteq.domain.event

import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class SagaEventSpec extends Specification {

  static final UUID SAGA_ID = UUID.fromString('2a1707c7-6e20-468e-b321-2a94dd2adbbd')

  def 'builder with all attributes'() {
    when:
    def sagaEvent = TestSagaEvent.builder()
          .sagaId(SAGA_ID)
          .build()
    then:
    sagaEvent.sagaId == SAGA_ID
  }

  def 'builder with no attributes'() {
    when:
    def sagaEvent = TestSagaEvent.builder()
          .build()
    then:
    noExceptionThrown()
    sagaEvent.sagaId != null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(TestSagaEvent)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .verify()
  }

  def 'toString returns the expected string'() {
    given:
    def event = TestSagaEvent.builder()
          .sagaId(SAGA_ID)
          .build()

    when:
    def string = event.toString()

    then:
    string.startsWith('TestSagaEvent(')
  }
}
