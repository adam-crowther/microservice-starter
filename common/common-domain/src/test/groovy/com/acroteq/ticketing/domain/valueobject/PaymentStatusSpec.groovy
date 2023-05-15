package com.acroteq.ticketing.domain.valueobject

import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.PaymentStatus.*
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class PaymentStatusSpec extends Specification {
  def "the enum should include only the expected values"() {
    when:
      def values = List.of(values())
    then:
      expect values, containsInAnyOrder(PENDING, COMPLETED, CANCELLED, FAILED)
  }
}
