package com.acroteq.ticketing.application.mapper

import com.acroteq.ticketing.domain.validation.ValidationResult
import org.mapstruct.factory.Mappers
import spock.lang.Specification

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.APPROVED
import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED

class ValidationResultMapperSpec extends Specification {

  static final String FAILURE_MESSAGE_1 = "failure-message-1"
  static final String FAILURE_MESSAGE_2 = "failure-message-2"
  static final String FAILURE_MESSAGE_3 = "failure-message-3"

  ValidationResultMapper mapper = Mappers.getMapper(ValidationResultMapper)

  def "converts from strings to validation result correctly"() {
    when:
      def result = mapper.convert(failureMessages)

    then:
      result.getApprovalStatus() == approvalStatus
      result.isPass() == isPass
      result.getFailures().size() == failureMessageCount
      result.getFailures() == outputMessages

    where:
      failureMessages                                           | approvalStatus | isPass | failureMessageCount | outputMessages
      [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3] | REJECTED       | false  | 3                   | [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3]
      [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2]                    | REJECTED       | false  | 2                   | [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2]
      [FAILURE_MESSAGE_1]                                       | REJECTED       | false  | 1                   | [FAILURE_MESSAGE_1]
      []                                                        | APPROVED       | true   | 0                   | []
      null                                                      | APPROVED       | true   | 0                   | []
  }

  def "converts from validation result to strings correctly"() {
    given:
      def validationResult = ValidationResult.of(failureMessages)

    when:
      def messages = mapper.convert(validationResult)

    then:
      messages.size() == failureMessageCount
      messages == outputMessages

    where:
      failureMessages                                           | failureMessageCount | outputMessages
      [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3] | 3                   | [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2, FAILURE_MESSAGE_3]
      [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2]                    | 2                   | [FAILURE_MESSAGE_1, FAILURE_MESSAGE_2]
      [FAILURE_MESSAGE_1]                                       | 1                   | [FAILURE_MESSAGE_1]
      []                                                        | 0                   | []
  }
}
