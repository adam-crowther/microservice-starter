package com.acroteq.ticketing.domain.valueobject

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class AirlineIdSpec extends Specification {

  static final Long ID = 394875

  def "when created with a valid ID, the entityId should return the correct value"() {
    when:
      def airlineId = AirlineId.of(ID)
    then:
      airlineId.getValue() == ID
  }

  def "when created with a null ID, the entityId should throw a NullPointerException"() {
    when:
      AirlineId.of(null)
    then:
      thrown(NullPointerException)
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(AirlineId)
            .withRedefinedSuperclass()
            .suppress(STRICT_INHERITANCE)

    then:
      verifier.verify()
  }

  def "toString returns the expected String"() {
    given:
      def dto = AirlineId.of(ID)

    when:
      def string = dto.toString()

    then:
      string == "AirlineId(super=394875)"
  }
}
