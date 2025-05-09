package com.acroteq.ticketing.payment.service.domain.entity

import com.acroteq.domain.valueobject.OrderId
import com.acroteq.domain.valueobject.PaymentId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.domain.valueobject.PaymentStatus.CANCELLED
import static com.acroteq.domain.valueobject.PaymentStatus.COMPLETED
import static com.acroteq.domain.valueobject.PaymentStatus.FAILED
import static com.acroteq.domain.valueobject.PaymentStatus.PENDING
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createAudit
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCashValue
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCustomer
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class PaymentSpec extends Specification {

  static final PaymentId PAYMENT_ID = PaymentId.of(987)
  static final OrderId ORDER_ID = OrderId.of(654)
  static final Long VERSION = 2
  static final Long ORDER_VERSION = 3

  def 'calling the builder with all attributes should set them on the entity and getters should them back the same'() {
    given:
    def testCustomer = createCustomer()
    def testAudit = createAudit()
    def testCashValue = createCashValue()

    when:
    def payment = Payment.builder()
          .id(PAYMENT_ID)
          .version(VERSION)
          .audit(testAudit)
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(testCustomer)
          .value(testCashValue)
          .status(PENDING)
          .build()
    then:
    payment.with {
      id == PAYMENT_ID
      version == VERSION
      audit == testAudit
      orderId == ORDER_ID
      orderVersion == ORDER_VERSION
      customer == testCustomer
      value == testCashValue
      status == PENDING
    }
  }

  def 'calling the builder with missing id, version or audit fields should not throw an exception'() {
    given:
    def customer = createCustomer()
    def value = createCashValue()

    when:
    def payment = Payment.builder()
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(customer)
          .value(value)
          .status(PENDING)
          .build()

    then:
    payment.id == null
    payment.version == null
    payment.audit == null
  }

  def 'calling the builder with missing data should throw an exception'() {
    when:
    Payment.builder()
          .orderId(orderId)
          .orderVersion(orderVersion)
          .customer(customer)
          .value(value)
          .status(status)
          .build()

    then:
    thrown(NullPointerException)

    where:
    orderId  | orderVersion  | customer         | value             | status
    null     | ORDER_VERSION | createCustomer() | createCashValue() | PENDING
    ORDER_ID | null          | createCustomer() | createCashValue() | PENDING
    ORDER_ID | ORDER_VERSION | null             | createCashValue() | PENDING
    ORDER_ID | ORDER_VERSION | createCustomer() | null              | PENDING
    ORDER_ID | ORDER_VERSION | createCustomer() | createCashValue() | null
  }

  def 'validate should throw an exception if the payment amount is less than or equal to zero'() {
    given:
    def customer = createCustomer()
    def value = createCashValue(amount)
    def payment = Payment.builder()
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(customer)
          .value(value)
          .status(PENDING)
          .build()

    when:
    def result = payment.validate()

    then:
    result.pass == pass
    if (!pass) {
      result.failures.size() == 1
      result.failures.get(0) == errorMessage
    }

    where:
    amount               || pass  | errorMessage
    BigDecimal.TEN       || true  | ''
    BigDecimal.ZERO      || false | 'Total price must be greater than zero'
    new BigDecimal(-400) || false | 'Total price must be greater than zero'
  }

  def 'cancel should throw an exception if the payment is not in state COMPLETED'() {
    given:
    def customer = createCustomer()
    def value = createCashValue()
    def payment = Payment.builder()
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(customer)
          .value(value)
          .status(status)
          .build()

    when:
    def result = payment.cancel()

    then:
    result.pass == pass
    if (!pass) {
      result.failures.size() == 1
      result.failures.get(0) == errorMessage
    }

    where:
    status    || pass  | errorMessage
    COMPLETED || true  | ''
    PENDING   || false | 'Only COMPLETED payments can be cancelled'
    CANCELLED || false | 'Only COMPLETED payments can be cancelled'
    FAILED    || false | 'Only COMPLETED payments can be cancelled'
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(Payment)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .withOnlyTheseFields('id')
          .verify()
  }

  def 'toString returns the expected string'() {
    given:
    def customer = createCustomer()
    def audit = createAudit()
    def value = createCashValue()
    def payment = Payment.builder()
          .id(PAYMENT_ID)
          .version(VERSION)
          .audit(audit)
          .orderId(ORDER_ID)
          .orderVersion(ORDER_VERSION)
          .customer(customer)
          .value(value)
          .status(PENDING)
          .build()

    when:
    def string = payment.toString()

    then:
    string.startsWith('Payment(')
  }
}

