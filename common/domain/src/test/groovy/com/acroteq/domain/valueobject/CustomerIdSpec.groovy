package com.acroteq.domain.valueobject

import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class CustomerIdSpec extends Specification {

  static final Long ID = 394875

  def 'when created with a valid ID, the entityId should return the correct value'() {
    when:
    def customerId = CustomerId.of(ID)
    then:
    customerId.value == ID
  }

  def 'when created with a null ID, the entityId should throw a NullPointerException'() {
    when:
    CustomerId.of(null)
    then:
    thrown(NullPointerException)
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(CustomerId)
          .withRedefinedSuperclass()
          .suppress(STRICT_INHERITANCE)

    then:
    verifier.verify()
  }

  def 'toString returns the expected String'() {
    given:
    def dto = CustomerId.of(ID)

    when:
    def string = dto.toString()

    then:
    string == 'CustomerId(super=394875)'
  }
}
