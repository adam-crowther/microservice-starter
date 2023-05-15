package com.acroteq.ticketing.application.dto

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

/**
 * Lombok removes boilerplate and makes code easier to read, but the tradeoff is that we need to verify the contract.
 **/
class AuditDtoSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1683832517)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1678344706)

  def "values should be set correctly"() {
    when:
      def dto = AuditDto.builder()
            .createdTimestamp(inputCreated)
            .lastModifiedTimestamp(inputLastModified)
            .build()

    then:
      dto.getCreatedTimestamp() == expectedCreated
      dto.getLastModifiedTimestamp() == expectedLastModified

    where:
      inputCreated      | inputLastModified       | expectedCreated   | expectedLastModified
      CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP | CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP
      null              | LAST_MODIFIED_TIMESTAMP | null              | LAST_MODIFIED_TIMESTAMP
      CREATED_TIMESTAMP | null                    | CREATED_TIMESTAMP | null
      null              | null                    | null              | null
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(AuditDto)

    then:
      verifier.verify()
  }

  def "toString returns the expected string"() {
    given:
      def dto = AuditDto.builder()
            .createdTimestamp(inputCreated)
            .lastModifiedTimestamp(inputLastModified)
            .build()

    when:
      def string = dto.toString()

    then:
      string == expected

    where:
      inputCreated      | inputLastModified       | expected
      CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP | "AuditDto(createdTimestamp=2023-05-11T19:15:17Z, lastModifiedTimestamp=2023-03-09T06:51:46Z)"
      null              | LAST_MODIFIED_TIMESTAMP | "AuditDto(createdTimestamp=null, lastModifiedTimestamp=2023-03-09T06:51:46Z)"
      CREATED_TIMESTAMP | null                    | "AuditDto(createdTimestamp=2023-05-11T19:15:17Z, lastModifiedTimestamp=null)"
      null              | null                    | "AuditDto(createdTimestamp=null, lastModifiedTimestamp=null)"
  }
}
