package com.acroteq.ticketing.domain.valueobject

import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

import static org.mockito.Mockito.mockStatic

class AuditSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1684346670390)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1684336284765)
  static final Instant TIMESTAMP_NOW = Instant.ofEpochSecond(1607045024691)

  def "when created with all attributes it should return the values correctly"() {
    when:
      def audit = Audit.builder()
            .createdTimestamp(CREATED_TIMESTAMP)
            .lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP)
            .build()

    then:
      audit.getCreatedTimestamp() == CREATED_TIMESTAMP
      audit.getLastModifiedTimestamp() == LAST_MODIFIED_TIMESTAMP
  }

  def "'now' method should set the values to the current date and time"() {
    given:
      def instantHolder = mockStatic(Instant.class)
      instantHolder.when(Instant::now).thenReturn(TIMESTAMP_NOW)

    when:
      def audit = Audit.now()

    then:
      audit.getCreatedTimestamp() == TIMESTAMP_NOW
      audit.getLastModifiedTimestamp() == TIMESTAMP_NOW
  }
  
  def "attributes should be nullable"() {
    when:
      def audit = Audit.builder().build()

    then:
      audit.getCreatedTimestamp() == null
      audit.getLastModifiedTimestamp() == null
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(Audit)

    then:
      verifier.verify()
  }
}
