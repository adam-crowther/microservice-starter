package com.acroteq.ticketing.application.mapper

import com.acroteq.application.mapper.ValidationResultMapper
import com.acroteq.domain.validation.ValidationResult
import groovy.transform.CompileDynamic
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.acroteq.domain.valueobject.OrderApprovalStatus.APPROVED
import static com.acroteq.domain.valueobject.OrderApprovalStatus.REJECTED

@CompileDynamic
class ValidationResultMapperSpec extends Specification {

  static final String MESSAGE_1 = 'message-1'
  static final String MESSAGE_2 = 'message-2'
  static final String MESSAGE_3 = 'message-3'

  ValidationResultMapper mapper = Mappers.getMapper(ValidationResultMapper)

  def 'converts from strings to validation result correctly'() {
    when:
    def result = mapper.convert(failureMessages)

    then:
    result.approvalStatus == approvalStatus
    result.pass == isPass
    result.failures.size() == failureMessageCount
    result.failures == outputMessages

    where:
    failureMessages                   || approvalStatus | isPass | failureMessageCount | outputMessages
    [MESSAGE_1, MESSAGE_2, MESSAGE_3] || REJECTED       | false  | 3                   | [MESSAGE_1, MESSAGE_2, MESSAGE_3]
    [MESSAGE_1, MESSAGE_2]            || REJECTED       | false  | 2                   | [MESSAGE_1, MESSAGE_2]
    [MESSAGE_1]                       || REJECTED       | false  | 1                   | [MESSAGE_1]
    []                                || APPROVED       | true   | 0                   | []
    null                              || APPROVED       | true   | 0                   | []
  }

  def 'converts from validation result to strings correctly'() {
    given:
    def validationResult = ValidationResult.of(failureMessages)

    when:
    def messages = mapper.convert(validationResult)

    then:
    messages.size() == failureMessageCount
    messages == outputMessages

    where:
    failureMessages                   || failureMessageCount | outputMessages
    [MESSAGE_1, MESSAGE_2, MESSAGE_3] || 3                   | [MESSAGE_1, MESSAGE_2, MESSAGE_3]
    [MESSAGE_1, MESSAGE_2]            || 2                   | [MESSAGE_1, MESSAGE_2]
    [MESSAGE_1]                       || 1                   | [MESSAGE_1]
    []                                || 0                   | []
  }
}
