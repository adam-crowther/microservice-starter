package com.acroteq.ticketing.domain.valueobject

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.CurrencyId.NONE

class CashValueSpec extends Specification {

  static final CurrencyId CURRENCY_CHF = CurrencyId.of("CHF")
  static final CurrencyId CURRENCY_EUR = CurrencyId.of("EUR")
  static final BigDecimal AMOUNT = BigDecimal.valueOf(5000)

  def "when created with all attributes it should return the values correctly"() {
    when:
      def cashValue = CashValue.builder()
            .currencyId(CURRENCY_CHF)
            .amount(AMOUNT)
            .build()

    then:
      cashValue.getCurrencyId() == CURRENCY_CHF
      cashValue.getAmount() == AMOUNT
  }

  def "when created with an attribute is missing it should throw a NullPointerException"() {
    when:
      def builder = CashValue.builder()
      Optional.ofNullable(currencyId).ifPresent { builder::currencyId }
      Optional.ofNullable(amount).ifPresent { builder::amount }
      builder.build()

    then:
      thrown(NullPointerException)

    where:
      currencyId   | amount
      CURRENCY_CHF | null
      null         | AMOUNT
  }

  def "when created with an attribute is null it should throw a NullPointerException"() {
    when:
      CashValue.builder()
            .currencyId(currencyId)
            .amount(amount)
            .build()

    then:
      thrown(NullPointerException)

    where:
      currencyId   | amount
      CURRENCY_CHF | null
      null         | AMOUNT
  }

  def "equals should compare the 2 values and return true if this has an amount that is more than the other"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def result = value.equals(otherValue)

    then:
      result == expectedResult

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | true
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 4000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | false
      NONE         | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | false
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 5000   | NONE            | 0           | false
      CURRENCY_CHF | -5000  | NONE            | 0           | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | true
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 0      | NONE            | 0           | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | false
      NONE         | 0      | CURRENCY_CHF    | 0           | false
      NONE         | 0      | NONE            | 0           | true
  }

  def "isGreaterThan should compare the 2 values and return true if this has an amount that is more than the other"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def result = value.isGreaterThan(otherValue)

    then:
      result == expectedResult

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 4000        | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | false
      NONE         | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | true
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | true
      CURRENCY_CHF | 5000   | NONE            | 0           | true
      CURRENCY_CHF | -5000  | NONE            | 0           | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | false
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 0      | NONE            | 0           | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | false
      NONE         | 0      | CURRENCY_CHF    | 0           | false
  }

  def "isGreaterThan should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.isGreaterThan(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000
      CURRENCY_CHF | 5000   | NONE            | 5000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000
      NONE         | 5000   | CURRENCY_CHF    | 5000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000
      CURRENCY_CHF | 5000   | NONE            | 4000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000
      NONE         | 5000   | CURRENCY_CHF    | 4000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000
      CURRENCY_CHF | 5000   | NONE            | 6000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000
      NONE         | 5000   | CURRENCY_CHF    | 6000
  }

  def "isGreaterThanOrEqualTo should compare the 2 values and return true if this has an amount that is more than or equal to the other"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def result = value.isGreaterThanOrEqualTo(otherValue)

    then:
      result == expectedResult

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 4000        | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | false
      NONE         | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | true
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | true
      CURRENCY_CHF | 5000   | NONE            | 0           | true
      CURRENCY_CHF | -5000  | NONE            | 0           | false
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | true
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | true
      CURRENCY_CHF | 0      | NONE            | 0           | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | true
      NONE         | 0      | CURRENCY_CHF    | 0           | true
  }

  def "isGreaterThanOrEqualTo should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.isGreaterThanOrEqualTo(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000        | null
      CURRENCY_CHF | 5000   | NONE            | 5000        | null
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000        | null
      NONE         | 5000   | CURRENCY_CHF    | 5000        | null
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000        | null
      CURRENCY_CHF | 5000   | NONE            | 4000        | null
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000        | null
      NONE         | 5000   | CURRENCY_CHF    | 4000        | null
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000        | null
      CURRENCY_CHF | 5000   | NONE            | 6000        | null
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000        | null
      NONE         | 5000   | CURRENCY_CHF    | 6000        | null
  }

  def "isLessThan should compare the 2 values and return true if this has an amount that is less than the other"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def result = value.isLessThan(otherValue)

    then:
      result == expectedResult

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 4000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 6000        | true
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | true
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | true
      NONE         | 0      | CURRENCY_CHF    | 6000        | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | false
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 5000   | NONE            | 0           | false
      CURRENCY_CHF | -5000  | NONE            | 0           | true
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | false
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 0      | NONE            | 0           | false
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | false
      NONE         | 0      | CURRENCY_CHF    | 0           | false
  }


  def "isLessThan should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.isLessThan(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000
      CURRENCY_CHF | 5000   | NONE            | 5000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000
      NONE         | 5000   | CURRENCY_CHF    | 5000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000
      CURRENCY_CHF | 5000   | NONE            | 4000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000
      NONE         | 5000   | CURRENCY_CHF    | 4000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000
      CURRENCY_CHF | 5000   | NONE            | 6000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000
      NONE         | 5000   | CURRENCY_CHF    | 6000
  }

  def "isLessThanOrEqualTo should compare the 2 values and return true if this has an amount that is less than or equal to the other"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def result = value.isLessThanOrEqualTo(otherValue)

    then:
      result == expectedResult

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedResult
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | true
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 4000        | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 6000        | true
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | true
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | true
      NONE         | 0      | CURRENCY_CHF    | 6000        | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | false
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | false
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | false
      CURRENCY_CHF | 5000   | NONE            | 0           | false
      CURRENCY_CHF | -5000  | NONE            | 0           | true
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | true
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | true
      CURRENCY_CHF | 0      | NONE            | 0           | true
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | true
      NONE         | 0      | CURRENCY_CHF    | 0           | true
  }

  def "isLessThanOrEqualTo should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.isLessThanOrEqualTo(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000
      CURRENCY_CHF | 5000   | NONE            | 5000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000
      NONE         | 5000   | CURRENCY_CHF    | 5000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000
      CURRENCY_CHF | 5000   | NONE            | 4000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000
      NONE         | 5000   | CURRENCY_CHF    | 4000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000
      CURRENCY_CHF | 5000   | NONE            | 6000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000
      NONE         | 5000   | CURRENCY_CHF    | 6000
  }

  def "add should return a new cash value containing the sum of the 2 amounts"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def sum = value.add(otherValue)

    then:
      sum.getAmount() == expectedAmount
      sum.getCurrencyId() == expectedCurrency

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedAmount | expectedCurrency
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | 10000          | CURRENCY_CHF
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | 1000           | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | 6000           | CURRENCY_CHF
      NONE         | 0      | CURRENCY_CHF    | 6000        | 6000           | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | 6000           | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | -5000          | CURRENCY_CHF
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | 5000   | NONE            | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | -5000  | NONE            | 0           | -5000          | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | 0              | CURRENCY_CHF
      CURRENCY_CHF | 0      | NONE            | 0           | 0              | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_EUR
      NONE         | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_CHF
  }

  def "add should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.add(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000
      CURRENCY_CHF | 5000   | NONE            | 5000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000
      NONE         | 5000   | CURRENCY_CHF    | 5000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000
      CURRENCY_CHF | 5000   | NONE            | 4000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000
      NONE         | 5000   | CURRENCY_CHF    | 4000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000
      CURRENCY_CHF | 5000   | NONE            | 6000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000
      NONE         | 5000   | CURRENCY_CHF    | 6000
  }

  def "subtract should return a new cash value containing the difference of the 2 amounts"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      def sum = value.subtract(otherValue)

    then:
      sum.getAmount() == expectedAmount
      sum.getCurrencyId() == expectedCurrency

    where:
      currencyId   | amount | otherCurrencyId | otherAmount | expectedAmount | expectedCurrency
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 5000        | 0              | CURRENCY_CHF
      CURRENCY_CHF | -5000  | CURRENCY_CHF    | 6000        | -11000         | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 6000        | -6000          | CURRENCY_CHF
      NONE         | 0      | CURRENCY_CHF    | 6000        | -6000          | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 6000        | -6000          | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | -5000       | 5000           | CURRENCY_CHF
      CURRENCY_CHF | 5000   | CURRENCY_CHF    | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | 5000   | NONE            | 0           | 5000           | CURRENCY_CHF
      CURRENCY_CHF | -5000  | NONE            | 0           | -5000          | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_CHF
      CURRENCY_CHF | 0      | CURRENCY_EUR    | 0           | 0              | CURRENCY_CHF
      CURRENCY_CHF | 0      | NONE            | 0           | 0              | CURRENCY_CHF
      CURRENCY_EUR | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_EUR
      NONE         | 0      | CURRENCY_CHF    | 0           | 0              | CURRENCY_CHF
  }

  def "subtract should throw an exception if the 2 currencies don't match"() {
    given:
      def value = CashValue.builder()
            .currencyId(currencyId)
            .amount(BigDecimal.valueOf(amount))
            .build()
      def otherValue = CashValue.builder()
            .currencyId(otherCurrencyId)
            .amount(BigDecimal.valueOf(otherAmount))
            .build()
    when:
      value.subtract(otherValue)

    then:
      thrown(IllegalArgumentException)

    where:
      currencyId   | amount | otherCurrencyId | otherAmount
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 5000
      CURRENCY_CHF | 5000   | NONE            | 5000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 5000
      NONE         | 5000   | CURRENCY_CHF    | 5000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 4000
      CURRENCY_CHF | 5000   | NONE            | 4000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 4000
      NONE         | 5000   | CURRENCY_CHF    | 4000
      CURRENCY_CHF | 5000   | CURRENCY_EUR    | 6000
      CURRENCY_CHF | 5000   | NONE            | 6000
      CURRENCY_EUR | 5000   | CURRENCY_CHF    | 6000
      NONE         | 5000   | CURRENCY_CHF    | 6000
  }

  def "multiply should return a new cash value containing the product of the 2 amounts"() {
    given:
      def value = CashValue.builder()
            .currencyId(CURRENCY_CHF)
            .amount(BigDecimal.valueOf(amount))
            .build()

    when:
      def product = value.multiply(quantity)

    then:
      product.getAmount() == expectedAmount
      product.getCurrencyId() == CURRENCY_CHF

    where:
      amount | quantity                | expectedAmount
      5000   | BigDecimal.valueOf(0)   | 0
      -5000  | BigDecimal.valueOf(0)   | 0
      0      | BigDecimal.valueOf(0)   | 0
      5000   | BigDecimal.valueOf(0.5) | 2500
      -5000  | BigDecimal.valueOf(0.5) | -2500
      0      | BigDecimal.valueOf(0.5) | 0
      5000   | BigDecimal.valueOf(1)   | 5000
      -5000  | BigDecimal.valueOf(1)   | -5000
      0      | BigDecimal.valueOf(1)   | 0
      5000   | BigDecimal.valueOf(1.5) | 7500
      -5000  | BigDecimal.valueOf(1.5) | -7500
      0      | BigDecimal.valueOf(1.5) | 0
      5000   | BigDecimal.valueOf(2)   | 10000
      -5000  | BigDecimal.valueOf(2)   | -10000
      0      | BigDecimal.valueOf(2)   | 0
      5000   | BigDecimal.valueOf(2.5) | 12500
      -5000  | BigDecimal.valueOf(2.5) | -12500
      0      | BigDecimal.valueOf(2.5) | 0
  }

  def "divide should return a new cash value containing the product of the 2 amounts"() {
    given:
      def value = CashValue.builder()
            .currencyId(CURRENCY_CHF)
            .amount(BigDecimal.valueOf(amount))
            .build()

    when:
      def product = value.divide(quantity)


    then:
      product.getAmount() == expectedAmount
      product.getCurrencyId() == CURRENCY_CHF

    where:
      amount | quantity                | expectedAmount
      5000   | BigDecimal.valueOf(0.5) | 10000
      -5000  | BigDecimal.valueOf(0.5) | -10000
      0      | BigDecimal.valueOf(0.5) | 0
      5000   | BigDecimal.valueOf(1)   | 5000
      -5000  | BigDecimal.valueOf(1)   | -5000
      0      | BigDecimal.valueOf(1)   | 0
      5000   | BigDecimal.valueOf(2)   | 2500
      -5000  | BigDecimal.valueOf(2)   | -2500
      0      | BigDecimal.valueOf(2)   | 0
      5000   | BigDecimal.valueOf(2.5) | 2000
      -5000  | BigDecimal.valueOf(2.5) | -2000
      0      | BigDecimal.valueOf(2.5) | 0
  }

  def "absolute return a new cash value containing the absolute of the amount"() {
    given:
      def value = CashValue.builder()
            .currencyId(CURRENCY_CHF)
            .amount(BigDecimal.valueOf(amount))
            .build()

    when:
      def product = value.absolute()

    then:
      product.getAmount() == expectedAmount
      product.getCurrencyId() == CURRENCY_CHF

    where:
      amount | expectedAmount
      5000   | 5000
      -5000  | 5000
      0      | 0
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(CashValue)

    then:
      verifier.verify()
  }
}
