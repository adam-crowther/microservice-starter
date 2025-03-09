package com.acroteq.ticketing.domain.valueobject

import com.acroteq.domain.valueobject.FlightId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class FlightIdSpec extends Specification {

  static final Long ID = 394875

  def 'when created with a valid ID, the entityId should return the correct value'() {
    when:
    def flightId = FlightId.of(ID)
    then:
    flightId.value == ID
  }

  def 'when created with a null ID, the entityId should throw a NullPointerException'() {
    when:
    FlightId.of(null)
    then:
    thrown(NullPointerException)
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(FlightId)
          .withRedefinedSuperclass()
          .suppress(STRICT_INHERITANCE)

    then:
    verifier.verify()
  }

  def 'toString returns the expected String'() {
    given:
    def dto = FlightId.of(ID)

    when:
    def string = dto.toString()

    then:
    string == 'FlightId(super=394875)'
  }
}
