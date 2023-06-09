package com.acroteq.ticketing.payment.service.domain.entity

import com.acroteq.ticketing.payment.service.domain.valueobject.CreditBalanceId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCashValue
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCustomer
import static java.math.BigDecimal.TEN
import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class CreditBalanceSpec extends Specification {

  static final CreditBalanceId TEST_ID = CreditBalanceId.of(987)
  static final Long VERSION = 2

  def 'calling the builder with all attributes should set them on the entity and getters should them back the same'() {
    given:
    def testTotalCredit = createCashValue()
    def testCustomer = createCustomer()

    when:
    def creditBalance = CreditBalance.builder()
          .id(TEST_ID)
          .version(VERSION)
          .totalCredit(testTotalCredit)
          .customer(testCustomer)
          .build()

    then:
    creditBalance.with {
      id == TEST_ID
      version == VERSION
      totalCredit == testTotalCredit
      customer == testCustomer
    }
  }

  def 'calling the builder with no id or version should just produce null values in the entity'() {
    given:
    def testTotalCredit = createCashValue()
    def testCustomer = createCustomer()

    when:
    def creditBalance = CreditBalance.builder()
          .totalCredit(testTotalCredit)
          .customer(testCustomer)
          .build()

    then:
    creditBalance.with {
      id == null
      version == null
      totalCredit == testTotalCredit
      customer == testCustomer
    }
  }

  def 'calling the builder with no totalCredit or customer should throw an exception'() {
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

  def 'addCredit should return a new CreditBalance object with an updated total credit plus the given amount'() {
    given:
    def testTotalCredit = createCashValue()
    def testAmount = createCashValue(TEN)
    def testCustomer = createCustomer()
    def testCreditBalance = CreditBalance.builder()
          .id(TEST_ID)
          .version(VERSION)
          .totalCredit(testTotalCredit)
          .customer(testCustomer)
          .build()

    when:
    def updatedCreditBalance = testCreditBalance.addCredit(testAmount)

    then:
    updatedCreditBalance.with {
      id == TEST_ID
      version == VERSION
      totalCredit.amount == 2010
      customer == testCustomer
    }
  }

  def 'subtractCredit should return a new CreditBalance object with an updated total credit minus the given amount'() {
    given:
    def testTotalCredit = createCashValue()
    def testAmount = createCashValue(TEN)
    def testCustomer = createCustomer()
    def testCreditBalance = CreditBalance.builder()
          .id(TEST_ID)
          .version(VERSION)
          .totalCredit(testTotalCredit)
          .customer(testCustomer)
          .build()

    when:
    def updatedCreditBalance = testCreditBalance.subtractCredit(testAmount)

    then:
    updatedCreditBalance.with {
      id == TEST_ID
      version == VERSION
      totalCredit.amount == 1990
      customer == testCustomer
    }
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(CreditBalance)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .withOnlyTheseFields('id')
          .verify()
  }

  def 'toString returns the expected string'() {
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
    string.startsWith('CreditBalance(')

    where:
    id      | version
    TEST_ID | VERSION
    null    | null
  }
}
