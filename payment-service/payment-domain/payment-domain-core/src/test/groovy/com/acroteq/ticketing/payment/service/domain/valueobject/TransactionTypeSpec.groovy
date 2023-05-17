package com.acroteq.ticketing.payment.service.domain.valueobject

import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.CREDIT
import static com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType.DEBIT
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class TransactionTypeSpec extends Specification {

  def "the enum should include only the expected values"() {
    when:
      def values = List.of(TransactionType.values())
    then:
      expect values, containsInAnyOrder(DEBIT, CREDIT)
  }

  def "static of() method should return the corresponding enum value"() {
    when:
      def result = TransactionType.of(value)
    then:
      result == expected
    where:
      value          || expected
      "debit"        || Optional.of(DEBIT)
      "credit"       || Optional.of(CREDIT)
      "unrecognised" || Optional.empty()
      null           || Optional.empty()
  }
}
