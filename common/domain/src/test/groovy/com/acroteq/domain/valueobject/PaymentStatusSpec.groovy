package com.acroteq.domain.valueobject

import groovy.transform.CompileDynamic
import spock.lang.Specification

import static com.acroteq.domain.valueobject.PaymentStatus.CANCELLED
import static com.acroteq.domain.valueobject.PaymentStatus.COMPLETED
import static com.acroteq.domain.valueobject.PaymentStatus.FAILED
import static com.acroteq.domain.valueobject.PaymentStatus.PENDING
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

@CompileDynamic
class PaymentStatusSpec extends Specification {

  def 'the enum should include only the expected values'() {
    when:
    def values = List.of(PaymentStatus.values())

    then:
    expect values, containsInAnyOrder(PENDING, COMPLETED, CANCELLED, FAILED)
  }

  def 'static of() method should return the corresponding enum value'() {
    when:
    def result = PaymentStatus.of(value)

    then:
    result == expected

    where:
    value          || expected
    'pending'      || Optional.of(PENDING)
    'completed'    || Optional.of(COMPLETED)
    'cancelled'    || Optional.of(CANCELLED)
    'failed'       || Optional.of(FAILED)
    'unrecognised' || Optional.empty()
    null           || Optional.empty()
  }
}
