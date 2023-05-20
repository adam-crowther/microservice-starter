package com.acroteq.ticketing.payment.service.domain.valueobject

import com.acroteq.ticketing.domain.validation.ValidationResult
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditBalance
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditChange
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createPayment

@CompileDynamic
class PaymentOutputSpec extends Specification {

  static final String FAILURE_MESSAGE = 'failure-message'

  def 'calling the builder with all attributes should set them on the entity and getters should them back the same'() {
    given:
    def testPayment = createPayment()
    def testOriginalCreditBalance = createCreditBalance()
    def testUpdatedCreditBalance = createCreditBalance()
    def testCreditChange = createCreditChange()
    def testValidationResult = ValidationResult.pass()

    when:
    def paymentOutput = PaymentOutput.builder()
          .payment(testPayment)
          .originalCreditBalance(testOriginalCreditBalance)
          .updatedCreditBalance(testUpdatedCreditBalance)
          .newCreditChange(testCreditChange)
          .validationResult(testValidationResult)
          .build()

    then:
    paymentOutput.with {
      payment == testPayment
      originalCreditBalance == testOriginalCreditBalance
      updatedCreditBalance == testUpdatedCreditBalance
      creditBalance == testUpdatedCreditBalance
      creditChange == Optional.of(testCreditChange)
      validationResult == testValidationResult
    }
  }

  def 'when the validation result is a fail, getCreditBalance should return the original'() {
    given:
    def testPayment = createPayment()
    def testOriginalCreditBalance = createCreditBalance()
    def testUpdatedCreditBalance = createCreditBalance()
    def testValidationResult = ValidationResult.fail(FAILURE_MESSAGE)

    when:
    def paymentOutput = PaymentOutput.builder()
          .payment(testPayment)
          .originalCreditBalance(testOriginalCreditBalance)
          .updatedCreditBalance(testUpdatedCreditBalance)
          .validationResult(testValidationResult)
          .build()

    then:
    paymentOutput.with {
      payment == testPayment
      originalCreditBalance == testOriginalCreditBalance
      updatedCreditBalance == testUpdatedCreditBalance
      creditBalance == testOriginalCreditBalance
      creditChange == Optional.empty()
      validationResult == testValidationResult
    }
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(PaymentOutput)

    then:
    verifier.verify()
  }

  def 'toString returns the expected string'() {
    given:
    def payment = createPayment()
    def originalCreditBalance = createCreditBalance()
    def updatedCreditBalance = createCreditBalance()
    def creditChange = createCreditChange()
    def validationResult = ValidationResult.pass()
    def paymentOutput = PaymentOutput.builder()
          .payment(payment)
          .originalCreditBalance(originalCreditBalance)
          .updatedCreditBalance(updatedCreditBalance)
          .newCreditChange(creditChange)
          .validationResult(validationResult)
          .build()

    when:
    def string = paymentOutput.toString()

    then:
    string.startsWith('PaymentOutput(')
  }
}
