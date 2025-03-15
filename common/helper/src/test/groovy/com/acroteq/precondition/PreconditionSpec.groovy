package com.acroteq.precondition

import groovy.transform.CompileDynamic
import spock.lang.Specification

import static Precondition.checkPrecondition

@CompileDynamic
class PreconditionSpec extends Specification {

  static final String MESSAGE = 'message'

  static final String PARAMETER_1 = 'parameter'
  static final String PARAMETER_2 = 'parameter'
  static final String PARAMETERISED_MESSAGE = 'message %s'
  static final String MESSAGE_WITH_PARAMETER = 'message parameter'

  def 'checkPrecondition should not throw an exception if the given expression returns true'() {
    when:
    checkPrecondition(true) { new TestException() }

    then:
    noExceptionThrown()
  }

  def 'checkPrecondition should throw an exception if the given expression returns false'() {
    when:
    checkPrecondition(false) { new TestException() }

    then:
    thrown(TestException)
  }

  def 'checkPrecondition with a message should not throw an exception if the given expression returns true'() {
    when:
    checkPrecondition(true, MESSAGE) { parameter -> new TestException(parameter) }

    then:
    noExceptionThrown()
  }

  def 'checkPrecondition with a message should throw an exception if the given expression returns false'() {
    when:
    checkPrecondition(false, MESSAGE) { parameter -> new TestException(parameter) }

    then:
    def exception = thrown(TestException)
    exception.message == MESSAGE
  }

  def 'checkPrecondition with message and parameter should not throw an exception if the given expression returns true'() {
    when:
    checkPrecondition(true, PARAMETERISED_MESSAGE, { parameter -> new TestException(parameter) }, PARAMETER_1)

    then:
    noExceptionThrown()
  }

  def 'checkPrecondition with message and parameter should throw an exception if the given expression returns false'() {
    when:
    checkPrecondition(false, PARAMETERISED_MESSAGE, { parameter -> new TestException(parameter) }, PARAMETER_1)

    then:
    def exception = thrown(TestException)
    exception.message == MESSAGE_WITH_PARAMETER
  }

  def 'checkPrecondition with parameter should not throw an exception if the given expression returns true'() {
    when:
    checkPrecondition(true, { parameter -> new TestException(parameter) }, PARAMETER_1)

    then:
    noExceptionThrown()
  }

  def 'checkPrecondition with parameter should throw an exception if the given expression returns false'() {
    when:
    checkPrecondition(false, { parameter -> new TestException(parameter) }, PARAMETER_1)

    then:
    def exception = thrown(TestException)
    exception.message == PARAMETER_1
  }

  def 'checkPrecondition with 2 parameters should not throw an exception if the given expression returns true'() {
    when:
    checkPrecondition(true, { parameter -> new TestException(parameter) }, PARAMETER_1, PARAMETER_2)

    then:
    noExceptionThrown()
  }

  def 'checkPrecondition with 2 parameters should throw an exception if the given expression returns false'() {
    when:
    checkPrecondition(false, { parameter1, parameter2 -> new TestException(parameter1, parameter2) }, PARAMETER_1, PARAMETER_2)

    then:
    def exception = thrown(TestException)
    exception.message == PARAMETER_1 + ',' + PARAMETER_2
  }
}
