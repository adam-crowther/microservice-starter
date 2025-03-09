package com.acroteq.ticketing.payment.service.domain.valueobject

import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditBalance
import static com.acroteq.ticketing.payment.service.domain.helper.TestDataHelper.createCreditChange

@CompileDynamic
class CreditBalanceOutputSpec extends Specification {

  def 'calling the builder with all attributes should set them on the entity and getters should them back the same'() {
    given:
    def creditBalance = createCreditBalance()
    def creditChange = createCreditChange()

    when:
    def creditBalanceOutput = CreditBalanceOutput.builder()
          .creditBalance(creditBalance)
          .creditChange(creditChange)
          .build()

    then:
    creditBalanceOutput.creditBalance == creditBalance
    creditBalanceOutput.creditChange == creditChange
  }

  def 'calling the builder with missing data should throw an exception'() {
    when:
    CreditBalanceOutput.builder()
          .creditBalance(creditBalance)
          .creditChange(creditChange)
          .build()

    then:
    thrown(NullPointerException)

    where:
    creditBalance         | creditChange
    null                  | createCreditChange()
    createCreditBalance() | null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(CreditBalanceOutput)

    then:
    verifier.verify()
  }

  def 'toString returns the expected string'() {
    def creditBalance = createCreditBalance()
    def creditChange = createCreditChange()
    def creditBalanceOutput = CreditBalanceOutput.builder()
          .creditBalance(creditBalance)
          .creditChange(creditChange)
          .build()

    when:
    def string = creditBalanceOutput.toString()

    then:
    string.startsWith('CreditBalanceOutput(')
  }
}
