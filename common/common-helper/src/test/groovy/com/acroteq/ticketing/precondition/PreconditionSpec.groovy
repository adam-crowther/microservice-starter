package com.acroteq.ticketing.precondition


import spock.lang.Specification

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition

class PreconditionSpec extends Specification {

  static final String MESSAGE = "message"

  static final String PARAMETER_1 = "parameter"
  static final String PARAMETER_2 = "parameter"
  static final String PARAMETERISED_MESSAGE = "message %s"
  static final String MESSAGE_WITH_PARAMETER = "message parameter"

  def "checkPrecondition should not throw an exception if the given expression returns true"() {
    when:
      checkPrecondition(true, { new TestException() })
    then:
      noExceptionThrown()
  }

  def "checkPrecondition should throw an exception if the given expression returns false"() {
    when:
      checkPrecondition(false, { new TestException() })
    then:
      thrown(TestException)
  }

  def "checkPrecondition with a message should not throw an exception if the given expression returns true"() {
    when:
      checkPrecondition(true, MESSAGE, { new TestException(it) })
    then:
      noExceptionThrown()
  }

  def "checkPrecondition with a message should throw an exception if the given expression returns false"() {
    when:
      checkPrecondition(false, MESSAGE, { new TestException(it) })
    then:
      def exception = thrown(TestException)
      exception.getMessage() == MESSAGE
  }

  def "checkPrecondition with message and parameter should not throw an exception if the given expression returns true"() {
    when:
      checkPrecondition(true, PARAMETERISED_MESSAGE, { new TestException(it) }, PARAMETER_1)
    then:
      noExceptionThrown()
  }

  def "checkPrecondition with message and parameter should throw an exception if the given expression returns false"() {
    when:
      checkPrecondition(false, PARAMETERISED_MESSAGE, { new TestException(it) }, PARAMETER_1)
    then:
      def exception = thrown(TestException)
      exception.getMessage() == MESSAGE_WITH_PARAMETER
  }

  def "checkPrecondition with parameter should not throw an exception if the given expression returns true"() {
    when:
      checkPrecondition(true, { new TestException(it) }, PARAMETER_1)
    then:
      noExceptionThrown()
  }

  def "checkPrecondition with parameter should throw an exception if the given expression returns false"() {
    when:
      checkPrecondition(false, { new TestException(it) }, PARAMETER_1)
    then:
      def exception = thrown(TestException)
      exception.getMessage() == PARAMETER_1
  }

  def "checkPrecondition with 2 parameters should not throw an exception if the given expression returns true"() {
    when:
      checkPrecondition(true, { new TestException(it) }, PARAMETER_1, PARAMETER_2)
    then:
      noExceptionThrown()
  }

  def "checkPrecondition with 2 parameters should throw an exception if the given expression returns false"() {
    when:
      checkPrecondition(false, { parameter1, parameter2 -> new TestException(parameter1, parameter2) }, PARAMETER_1, PARAMETER_2)
    then:
      def exception = thrown(TestException)
      exception.getMessage() == PARAMETER_1 + "," + PARAMETER_2
  }
}

class TestException extends RuntimeException {
  TestException() {}

  TestException(final String message) {
    super(message)
  }

  TestException(final String message1, final String message2) {
    super(message1 + "," + message2)
  }
}