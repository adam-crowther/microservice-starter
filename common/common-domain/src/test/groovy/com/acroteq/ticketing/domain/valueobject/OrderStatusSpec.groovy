package com.acroteq.ticketing.domain.valueobject

import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.OrderStatus.APPROVED
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.CANCELLED
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.CANCELLING
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PAID
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PENDING
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

class OrderStatusSpec extends Specification {
  def "the enum should include only the expected values"() {
    when:
      def values = List.of(OrderStatus.values())
    then:
      expect values, containsInAnyOrder(PENDING, PAID, APPROVED, CANCELLING, CANCELLED)
  }
}
