package com.acroteq.ticketing.application.dto

import com.acroteq.application.dto.AuditDto
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

/**
 * Lombok removes boilerplate and makes code easier to read, but the tradeoff is that we need to verify the contract.
 **/
@CompileDynamic
class AuditDtoSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1683832517)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1678344706)

  def 'values should be set correctly'() {
    when:
    def dto = AuditDto.builder()
          .createdTimestamp(inputCreated)
          .lastModifiedTimestamp(inputLastModified)
          .build()

    then:
    dto.createdTimestamp == expectedCreated
    dto.lastModifiedTimestamp == expectedLastModified

    where:
    inputCreated      | inputLastModified       || expectedCreated   | expectedLastModified
    CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP || CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP
    null              | LAST_MODIFIED_TIMESTAMP || null              | LAST_MODIFIED_TIMESTAMP
    CREATED_TIMESTAMP | null                    || CREATED_TIMESTAMP | null
    null              | null                    || null              | null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(AuditDto)

    then:
    verifier.verify()
  }

  def 'toString returns the expected string'() {
    given:
    def dto = AuditDto.builder()
          .createdTimestamp(inputCreated)
          .lastModifiedTimestamp(inputLastModified)
          .build()

    when:
    def string = dto.toString()

    then:
    string.startsWith('AuditDto(')

    where:
    inputCreated      | inputLastModified
    CREATED_TIMESTAMP | LAST_MODIFIED_TIMESTAMP
    null              | LAST_MODIFIED_TIMESTAMP
    CREATED_TIMESTAMP | null
    null              | null
  }
}
