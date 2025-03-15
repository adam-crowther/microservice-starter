package com.acroteq.domain.exception

import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class DomainExceptionSpec extends Specification {

  static final String PARAMETER = 'parameter'

  def 'without a cause exception'() {
    when:
    def exception = new TestDomainException(parameter)

    then:
    exception.code == 'problem.test.domain.exception'
    exception.parameters == [parameter] as String[]
    exception.message == message
    exception.cause == null

    where:
    parameter || message
    PARAMETER || 'Test Domain Exception parameter'
    null      || 'Test Domain Exception null'
  }

  def 'with a cause exception'() {
    given:
    def cause = Mock(Throwable)

    when:
    def exception = new TestDomainException(parameter, cause)

    then:
    exception.code == 'problem.test.domain.exception'
    exception.parameters == [parameter] as String[]
    exception.message == message
    exception.cause == cause

    where:
    parameter || message
    PARAMETER || 'Test Domain Exception parameter'
    null      || 'Test Domain Exception null'
  }
}
