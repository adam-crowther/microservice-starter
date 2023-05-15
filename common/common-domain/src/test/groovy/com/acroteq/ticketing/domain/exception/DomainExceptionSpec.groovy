package com.acroteq.ticketing.domain.exception

import spock.lang.Specification

class DomainExceptionSpec extends Specification {

  static final String PARAMETER = "parameter"

  def "without a cause exception"() {
    when:
      def exception = new TestDomainException(parameter)

    then:
      exception.getCode() == "problem.test.domain.exception"
      exception.getParameters() == [parameter] as String[]
      exception.getMessage() == message
      exception.getCause() == null

    where:
      parameter | message
      PARAMETER | "Test Domain Exception parameter"
      null      | "Test Domain Exception null"
  }

  def "with a cause exception"() {
    given:
      def cause = Mock(Throwable)

    when:
      def exception = new TestDomainException(parameter, cause)

    then:
      exception.getCode() == "problem.test.domain.exception"
      exception.getParameters() == [parameter] as String[]
      exception.getMessage() == message
      exception.getCause() == cause

    where:
      parameter | message
      PARAMETER | "Test Domain Exception parameter"
      null      | "Test Domain Exception null"
  }
}
