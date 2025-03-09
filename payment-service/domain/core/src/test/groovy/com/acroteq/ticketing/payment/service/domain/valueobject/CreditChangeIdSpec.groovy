package com.acroteq.ticketing.payment.service.domain.valueobject

import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class CreditChangeIdSpec extends Specification {

  static final Long ID = 394875

  def 'when created with a valid ID, the entityId should return the correct value'() {
    when:
    def creditChangeId = CreditChangeId.of(ID)
    then:
    creditChangeId.value == ID
  }

  def 'when created with a null ID, the entityId should throw a NullPointerException'() {
    when:
    CreditChangeId.of(null)
    then:
    thrown(NullPointerException)
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(CreditChangeId)
          .withRedefinedSuperclass()
          .suppress(STRICT_INHERITANCE)

    then:
    verifier.verify()
  }

  def 'toString returns the expected String'() {
    given:
    def dto = CreditChangeId.of(ID)

    when:
    def string = dto.toString()

    then:
    string == 'CreditChangeId(super=394875)'
  }
}
