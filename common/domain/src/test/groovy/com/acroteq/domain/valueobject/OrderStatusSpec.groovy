package com.acroteq.ticketing.domain.valueobject

import com.acroteq.domain.valueobject.OrderStatus
import groovy.transform.CompileDynamic
import spock.lang.Specification

import static com.acroteq.domain.valueobject.OrderStatus.APPROVED
import static com.acroteq.domain.valueobject.OrderStatus.CANCELLED
import static com.acroteq.domain.valueobject.OrderStatus.CANCELLING
import static com.acroteq.domain.valueobject.OrderStatus.PAID
import static com.acroteq.domain.valueobject.OrderStatus.PENDING
import static org.hamcrest.Matchers.containsInAnyOrder
import static spock.util.matcher.HamcrestSupport.expect

@CompileDynamic
class OrderStatusSpec extends Specification {

  def 'the enum should include only the expected values'() {
    when:
    def values = List.of(OrderStatus.values())
    then:
    expect values, containsInAnyOrder(PENDING, PAID, APPROVED, CANCELLING, CANCELLED)
  }
}
