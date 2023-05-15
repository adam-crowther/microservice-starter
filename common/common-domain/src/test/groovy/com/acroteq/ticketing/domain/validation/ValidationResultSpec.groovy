package com.acroteq.ticketing.domain.validation


import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.APPROVED
import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED

class ValidationResultSpec extends Specification {
  static final String FAILURE_MESSAGE_1 = "failure-message-1"
  static final String FAILURE_MESSAGE_2 = "failure-message-2"
  static final String FAILURE_MESSAGE_3 = "failure-message-3"

  static final String PARAMETER = "parameter"
  static final String PARAMETERISED_FAILURE_MESSAGE = "failure-message: %s"
  static final String FAILURE_MESSAGE_WITH_PARAMETER = "failure-message: parameter"

  static final ValidationResult FAILED_RESULT_1 = ValidationResult.fail(FAILURE_MESSAGE_1)
  static final ValidationResult FAILED_RESULT_2 = ValidationResult.fail(FAILURE_MESSAGE_2)
  static final ValidationResult PASSED_RESULT_1 = ValidationResult.pass()
  static final ValidationResult PASSED_RESULT_2 = ValidationResult.pass()

  def "builder with failures should return a rejected result"() {
    when:
      def result = ValidationResult.builder()
            .failure(FAILURE_MESSAGE_1)
            .failure(FAILURE_MESSAGE_2)
            .failure(FAILURE_MESSAGE_3)
            .build()

    then:
      result.getFailures() == [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1 + "," + FAILURE_MESSAGE_2 + "," + FAILURE_MESSAGE_3
  }

  def "builder already containing the given message should not add a second failure message"() {
    when:
      def result = ValidationResult.builder()
            .validationResult(FAILED_RESULT_1)
            .failure(FAILURE_MESSAGE_1)
            .build()

    then:
      result.getFailures() == [FAILURE_MESSAGE_1]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1
  }

  def "builder without failures should return an approved result"() {
    when:
      def result = ValidationResult.builder().build()

    then:
      result.getFailures() == []
      result.getApprovalStatus() == APPROVED
      result.isPass()
      result.getLogOutput() == ""
  }

  def "builder with result and failures should return an rejected result"() {
    when:
      def result = ValidationResult.builder()
            .validationResult(FAILED_RESULT_1)
            .failure(FAILURE_MESSAGE_2)
            .failure(FAILURE_MESSAGE_3)
            .build()

    then:
      result.getFailures() == [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1 + "," + FAILURE_MESSAGE_2 + "," + FAILURE_MESSAGE_3
  }

  def "builder already containing the given message should not add a result with the same message"() {
    when:
      def result = ValidationResult.builder()
            .failure(FAILURE_MESSAGE_1)
            .validationResult(FAILED_RESULT_1)
            .build()

    then:
      result.getFailures() == [FAILURE_MESSAGE_1]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1
  }

  def "build failure with parameter should be formatted correctly"() {
    when:
      def result = ValidationResult.builder()
            .failure(PARAMETERISED_FAILURE_MESSAGE, PARAMETER)
            .build()

    then:
      result.getFailures() == [FAILURE_MESSAGE_WITH_PARAMETER]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_WITH_PARAMETER
  }

  def "build with null failure message throw a NullPointerException"() {
    when:
      ValidationResult.builder()
            .failure(null)
            .build()

    then:
      thrown(NullPointerException)
  }

  def "build with null failure message and parameter throw a NullPointerException"() {
    when:
      ValidationResult.builder()
            .failure(null, PARAMETER)
            .build()

    then:
      thrown(NullPointerException)
  }

  def "build with null result throw a NullPointerException"() {
    when:
      ValidationResult.builder()
            .validationResult(null)
            .build()

    then:
      thrown(NullPointerException)
  }

  def "build with blank failure not add a failure message"() {
    when:
      def result = ValidationResult.builder()
            .failure("")
            .build()

    then:
      result.getFailures() == []
      result.getApprovalStatus() == APPROVED
      result.isPass()
      result.getLogOutput() == ""
  }

  def "static constructor method pass() should return an approved result"() {
    when:
      def result = ValidationResult.pass()

    then:
      result.getFailures() == []
      result.getApprovalStatus() == APPROVED
      result.isPass()
      result.getLogOutput() == ""
  }

  def "static constructor method fail() should return a rejected result"() {
    when:
      def result = ValidationResult.fail(FAILURE_MESSAGE_1)

    then:
      result.getFailures() == [FAILURE_MESSAGE_1]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1
  }

  def "static constructor method fail() with a null message should throw a NullPointerException"() {
    when:
      def result = ValidationResult.fail(null)

    then:
      thrown(NullPointerException)
  }

  def "static constructor method fail() with a parameter should be formatted correctly"() {
    when:
      def result = ValidationResult.fail(PARAMETERISED_FAILURE_MESSAGE, PARAMETER)

    then:
      result.getFailures() == [FAILURE_MESSAGE_WITH_PARAMETER]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_WITH_PARAMETER
  }

  def "static constructor method fail() with a null message and parameter should throw a NullPointerException"() {
    when:
      def result = ValidationResult.fail(null, PARAMETER)

    then:
      thrown(NullPointerException)
  }

  def "create results using static constructor method of() with a list of validation results should be rejected"() {
    when:
      def result = ValidationResult.of([FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3])

    then:
      result.getFailures() == [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1 + "," + FAILURE_MESSAGE_2 + "," + FAILURE_MESSAGE_3
  }

  def "create results using static constructor method with an empty list should be rejected"() {
    when:
      def result = ValidationResult.of([])

    then:
      result.getFailures() == []
      result.getApprovalStatus() == APPROVED
      result.isPass()
      result.getLogOutput() == ""
  }

  def "create results using static constructor method with a null input should throw a NullPointerException"() {
    when:
      def result = ValidationResult.of(null)

    then:
      thrown(NullPointerException)
  }


  def "combine results with 2 failed results should be rejected"() {
    when:
      def result = ValidationResult.combine(FAILED_RESULT_1, FAILED_RESULT_2)

    then:
      result.getFailures() == [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1 + "," + FAILURE_MESSAGE_2
  }

  def "combine results with 1 failed and 1 passed results should be rejected"() {
    when:
      def result = ValidationResult.combine(FAILED_RESULT_1, PASSED_RESULT_1)

    then:
      result.getFailures() == [FAILURE_MESSAGE_1]
      result.getApprovalStatus() == REJECTED
      !result.isPass()
      result.getLogOutput() == FAILURE_MESSAGE_1
  }

  def "combine results with 2 passed results should be approved"() {
    when:
      def result = ValidationResult.combine(PASSED_RESULT_1, PASSED_RESULT_2)

    then:
      result.getFailures() == []
      result.getApprovalStatus() == APPROVED
      result.isPass()
      result.getLogOutput() == ""
  }

  def "combine results with a null parameter should throw a NullPointerException"() {
    when:
      def result = ValidationResult.combine(result1, result2)

    then:
      thrown(NullPointerException)

    where:
      result1         | result2
      FAILED_RESULT_1 | null
      null            | FAILED_RESULT_2
  }
}
