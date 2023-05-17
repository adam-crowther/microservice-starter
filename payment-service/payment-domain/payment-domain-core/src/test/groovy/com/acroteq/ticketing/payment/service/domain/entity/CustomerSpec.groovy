package com.acroteq.ticketing.payment.service.domain.entity


import com.acroteq.ticketing.domain.valueobject.CustomerId
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCashValue
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createEventId
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class CustomerSpec extends Specification {

  static final CustomerId CUSTOMER_ID = CustomerId.of(987)
  static final int VERSION = 2

  def "calling the builder with all attributes should set them on the entity and getters should them back the same"() {
    given:
      def creditLimit = createCashValue()
      def eventId = createEventId()

    when:
      def customer = Customer.builder()
            .id(CUSTOMER_ID)
            .version(VERSION)
            .eventId(eventId)
            .creditLimit(creditLimit)
            .build()
    then:
      customer.getId() == CUSTOMER_ID
      customer.getVersion() == VERSION
      customer.getCreditLimit() == creditLimit
  }

  def "calling the builder with missing data should throw an exception"() {
    when:
      Customer.builder()
            .id(id)
            .version(version)
            .creditLimit(creditLimit)
            .eventId(eventId)
            .build()

    then:
      thrown(NullPointerException)

    where:
      id          | version | creditLimit       | eventId
      null        | VERSION | createCashValue() | createEventId()
      CUSTOMER_ID | null    | createCashValue() | createEventId()
      CUSTOMER_ID | VERSION | null              | createEventId()
      CUSTOMER_ID | VERSION | createCashValue() | null
  }

  def "withZeroCreditLimit should return a new entity with the same values, and a creditLimit of ZERO"() {
    given:
      def creditLimit = createCashValue()
      def eventId = createEventId()
      def customer = Customer.builder()
            .id(CUSTOMER_ID)
            .version(VERSION)
            .eventId(eventId)
            .creditLimit(creditLimit)
            .build()

    when:
      def newCustomer = customer.withZeroCreditLimit()

    then:
      newCustomer.getId() == CUSTOMER_ID
      newCustomer.getVersion() == VERSION
      newCustomer.getCreditLimit() == ZERO
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(Customer)

    then:
      verifier.suppress(STRICT_INHERITANCE)
            .withOnlyTheseFields("id")
            .verify()
  }

  def "toString returns the expected string"() {
    given:
      def creditLimit = createCashValue()
      def eventId = createEventId()
      def customer = Customer.builder()
            .id(CUSTOMER_ID)
            .version(VERSION)
            .eventId(eventId)
            .creditLimit(creditLimit)
            .build()

    when:
      def string = customer.toString()

    then:
      string.startsWith("Customer(")
  }
}
