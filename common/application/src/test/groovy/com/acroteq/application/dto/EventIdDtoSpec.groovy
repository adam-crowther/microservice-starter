package com.acroteq.ticketing.application.dto

import com.acroteq.application.dto.EventIdDto
import groovy.transform.CompileDynamic
import jakarta.validation.Validation
import jakarta.validation.Validator
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

/**
 * Lombok removes boilerplate and makes code easier to read, but the tradeoff is that we need to verify the contract.
 **/
@CompileDynamic
class EventIdDtoSpec extends Specification {

  static final Long OFFSET = 123456
  static final Integer PARTITION = 2

  final Validator validator = Validation.buildDefaultValidatorFactory().validator

  def 'values should be set correctly'() {
    when:
    def dto = EventIdDto.builder()
          .offset(inputOffset)
          .partition(inputPartition)
          .build()

    then:
    dto.offset == expectedOffset
    dto.partition == expectedPartition

    where:
    // @NotNull is enforced by the Spring bean validation framework, which is not running in this test.
    inputOffset | inputPartition || expectedOffset | expectedPartition
    OFFSET      | PARTITION      || OFFSET         | PARTITION
    null        | PARTITION      || null           | PARTITION
    OFFSET      | null           || OFFSET         | null
    null        | null           || null           | null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(EventIdDto)

    then:
    verifier.verify()
  }

  def 'spring bean validation works correctly'() {
    given:
    def dto = EventIdDto.builder()
          .offset(inputOffset)
          .partition(inputPartition)
          .build()
    when:
    def violations = validator.validate(dto)

    then:
    violations.size() == expectedViolationCount

    where:
    // @NotNull is enforced by the Spring bean validation framework, which is not running in this test.
    inputOffset | inputPartition || expectedViolationCount
    OFFSET      | PARTITION      || 0
    null        | PARTITION      || 1
    OFFSET      | null           || 1
    null        | null           || 2
  }

  def 'toString returns the expected String'() {
    given:
    def dto = EventIdDto.builder()
          .offset(inputOffset)
          .partition(inputPartition)
          .build()

    when:
    def string = dto.toString()

    then:
    string.startsWith('EventIdDto(')

    where:
    inputOffset | inputPartition
    OFFSET      | PARTITION
    null        | PARTITION
    OFFSET      | null
    null        | null
  }
}
