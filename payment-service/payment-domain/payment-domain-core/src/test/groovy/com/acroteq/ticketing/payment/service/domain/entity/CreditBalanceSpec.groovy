package com.acroteq.ticketing.payment.service.domain.entity

import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.*
import static java.math.BigDecimal.TEN
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class CreditBalanceSpec extends Specification {

  static final CreditBalanceId TEST_ID = CreditBalanceId.of(987)
  static final int VERSION = 2

  def "calling the builder with all attributes should set them on the entity and getters should them back the same"() {
    given:
      def totalCredit = createCashValue()
      def customer = createCustomer()

    when:
      def creditBalance = CreditBalance.builder()
            .id(TEST_ID)
            .version(VERSION)
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    then:
      creditBalance.getId() == TEST_ID
      creditBalance.getVersion() == VERSION
      creditBalance.getTotalCredit() == totalCredit
      creditBalance.getCustomer() == customer
  }

  def "calling the builder with no id or version should just produce null values in the entity"() {
    given:
      def totalCredit = createCashValue()
      def customer = createCustomer()

    when:
      def creditBalance = CreditBalance.builder()
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    then:
      creditBalance.getId() == null
      creditBalance.getVersion() == null
      creditBalance.getTotalCredit() == totalCredit
      creditBalance.getCustomer() == customer
  }

  def "calling the builder with no totalCredit or customer should throw an exception"() {
    when:
      CreditBalance.builder()
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    then:
      thrown(NullPointerException)

    where:
      totalCredit       | customer
      null              | createCustomer()
      createCashValue() | null
  }

  def "addCredit should return a new CreditBalance object with an updated total credit plus the given amount"() {
    given:
      def totalCredit = createCashValue()
      def amount = createCashValue(TEN)
      def customer = createCustomer()
      def creditBalance = CreditBalance.builder()
            .id(TEST_ID)
            .version(VERSION)
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    when:
      def updatedCreditBalance = creditBalance.addCredit(amount)

    then:
      updatedCreditBalance.getId() == TEST_ID
      updatedCreditBalance.getVersion() == VERSION
      updatedCreditBalance.getTotalCredit().getAmount() == AMOUNT + TEN
      updatedCreditBalance.getCustomer() == customer
  }

  def "subtractCredit should return a new CreditBalance object with an updated total credit minus the given amount"() {
    given:
      def totalCredit = createCashValue()
      def amount = createCashValue(TEN)
      def customer = createCustomer()
      def creditBalance = CreditBalance.builder()
            .id(TEST_ID)
            .version(VERSION)
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    when:
      def updatedCreditBalance = creditBalance.subtractCredit(amount)

    then:
      updatedCreditBalance.getId() == TEST_ID
      updatedCreditBalance.getVersion() == VERSION
      updatedCreditBalance.getTotalCredit().getAmount() == AMOUNT - TEN
      updatedCreditBalance.getCustomer() == customer
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(CreditBalance)

    then:
      verifier.suppress(STRICT_INHERITANCE)
            .withOnlyTheseFields("id")
            .verify()
  }

  def "toString returns the expected string"() {
    given:
      def totalCredit = createCashValue()
      def customer = createCustomer()

      def creditBalance = CreditBalance.builder()
            .id(id)
            .version(version)
            .totalCredit(totalCredit)
            .customer(customer)
            .build()

    when:
      def string = creditBalance.toString()

    then:
      string.startsWith("CreditBalance(")

    where:
      id      | version
      TEST_ID | VERSION
      null    | null
  }
}
