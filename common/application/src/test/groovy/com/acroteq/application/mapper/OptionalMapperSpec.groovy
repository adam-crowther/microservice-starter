package com.acroteq.application.mapper

import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification


@CompileDynamic
class OptionalMapperSpec extends Specification {
  static final String STRING_VALUE = "string-value"

  OptionalMapper optionalMapper = Mappers.getMapper(OptionalMapper)

  def 'when the optional is present getMandatoryValue returns the value'() {
    given:
    def optional = Optional.of(STRING_VALUE)
    when:
    def value = optionalMapper.getMandatoryValue(optional)
    then:
    value == STRING_VALUE
  }

  def 'when the optional is empty getMandatoryValue throws a null pointer exception'() {
    given:
    def optional = Optional.empty()
    when:
    optionalMapper.getMandatoryValue(optional)
    then:
    thrown(NullPointerException)
  }

  def 'when the input is null getMandatoryValue throws a null pointer exception'() {
    when:
    optionalMapper.getMandatoryValue(null)
    then:
    thrown(NullPointerException)
  }

  def 'when the optional is present getOptionalValue returns the value'() {
    given:
    def optional = Optional.of(STRING_VALUE)
    when:
    def value = optionalMapper.getOptionalValue(optional)
    then:
    value == STRING_VALUE
  }

  def 'when the optional is empty getOptionalValue returns null'() {
    given:
    def optional = Optional.empty()
    when:
    def value = optionalMapper.getOptionalValue(optional)
    then:
    value == null
  }

  def 'when the input is null getOptionalValue throws a null pointer exception'() {
    when:
    optionalMapper.getOptionalValue(null)
    then:
    thrown(NullPointerException)
  }
}
