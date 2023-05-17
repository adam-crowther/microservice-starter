package com.acroteq.ticketing.payment.service.domain.valueobject

import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.CREDIT_LIMIT_UPDATE
import static com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType.PAYMENT
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class CreditChangeTypeSpec extends Specification {

  def "the enum should include only the expected values"() {
    when:
      def values = List.of(CreditChangeType.values())
    then:
      expect values, containsInAnyOrder(PAYMENT, CREDIT_LIMIT_UPDATE)
  }

  def "static of() method should return the corresponding enum value"() {
    when:
      def result = CreditChangeType.of(value)
    then:
      result == expected
    where:
      value                 || expected
      "payment"             || Optional.of(PAYMENT)
      "credit limit update" || Optional.of(CREDIT_LIMIT_UPDATE)
      "unrecognised"        || Optional.empty()
      null                  || Optional.empty()
  }
}
