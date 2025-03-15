package com.acroteq.domain.valueobject

import groovy.transform.CompileDynamic
import spock.lang.Specification

import static com.acroteq.domain.valueobject.OrderApprovalStatus.APPROVED
import static com.acroteq.domain.valueobject.OrderApprovalStatus.REJECTED
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

@CompileDynamic
class OrderApprovalStatusSpec extends Specification {

  def 'the enum should include only the expected values'() {
    when:
    def values = List.of(OrderApprovalStatus.values())

    then:
    expect values, containsInAnyOrder(APPROVED, REJECTED)
  }

  def 'static of() method should return the corresponding enum value'() {
    when:
    def result = OrderApprovalStatus.of(value)

    then:
    result == expected

    where:
    value          || expected
    'approved'     || Optional.of(APPROVED)
    'rejected'     || Optional.of(REJECTED)
    'unrecognised' || Optional.empty()
    null           || Optional.empty()
  }
}
