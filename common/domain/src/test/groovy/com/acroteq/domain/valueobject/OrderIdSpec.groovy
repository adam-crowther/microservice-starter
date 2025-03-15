package com.acroteq.domain.valueobject

import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class OrderIdSpec extends Specification {

  static final Long ID = 394875

  def 'when created with a valid ID, the entityId should return the correct value'() {
    when:
    def orderId = OrderId.of(ID)
    then:
    orderId.value == ID
  }

  def 'when created with a null ID, the entityId should throw a NullPointerException'() {
    when:
    OrderId.of(null)
    then:
    thrown(NullPointerException)
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(OrderId)
          .withRedefinedSuperclass()
          .suppress(STRICT_INHERITANCE)

    then:
    verifier.verify()
  }

  def 'toString returns the expected String'() {
    given:
    def dto = OrderId.of(ID)

    when:
    def string = dto.toString()

    then:
    string == 'OrderId(super=394875)'
  }
}
