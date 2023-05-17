package com.acroteq.ticketing.payment.service.domain.valueobject

import com.acroteq.ticketing.domain.validation.ValidationResult
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditBalance
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditChange
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createPayment

class PaymentOutputSpec extends Specification {

  static final String FAILURE_MESSAGE = "failure-message"

  def "calling the builder with all attributes should set them on the entity and getters should them back the same"() {
    given:
      def payment = createPayment()
      def originalCreditBalance = createCreditBalance()
      def updatedCreditBalance = createCreditBalance()
      def creditChange = createCreditChange()
      def validationResult = ValidationResult.pass()

    when:
      def paymentOutput = PaymentOutput.builder()
            .payment(payment)
            .originalCreditBalance(originalCreditBalance)
            .updatedCreditBalance(updatedCreditBalance)
            .newCreditChange(creditChange)
            .validationResult(validationResult)
            .build()

    then:
      paymentOutput.getPayment() == payment
      paymentOutput.getOriginalCreditBalance() == originalCreditBalance
      paymentOutput.getUpdatedCreditBalance() == updatedCreditBalance
      paymentOutput.getCreditBalance() == updatedCreditBalance
      paymentOutput.getCreditChange() == Optional.of(creditChange)
      paymentOutput.getValidationResult() == validationResult
  }

  def "when the validation result is a fail, getCreditBalance should return the original"() {
    given:
      def payment = createPayment()
      def originalCreditBalance = createCreditBalance()
      def updatedCreditBalance = createCreditBalance()
      def validationResult = ValidationResult.fail(FAILURE_MESSAGE)

    when:
      def paymentOutput = PaymentOutput.builder()
            .payment(payment)
            .originalCreditBalance(originalCreditBalance)
            .updatedCreditBalance(updatedCreditBalance)
            .validationResult(validationResult)
            .build()

    then:
      paymentOutput.getPayment() == payment
      paymentOutput.getOriginalCreditBalance() == originalCreditBalance
      paymentOutput.getUpdatedCreditBalance() == updatedCreditBalance
      paymentOutput.getCreditBalance() == originalCreditBalance
      paymentOutput.getCreditChange() == Optional.empty()
      paymentOutput.getValidationResult() == validationResult
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(PaymentOutput)

    then:
      verifier.verify()
  }

  def "toString returns the expected string"() {
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
      string.startsWith("PaymentOutput(")
  }
}
