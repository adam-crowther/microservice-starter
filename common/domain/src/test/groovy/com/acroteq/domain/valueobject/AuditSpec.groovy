package com.acroteq.ticketing.domain.valueobject

import com.acroteq.domain.valueobject.Audit
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

import static org.mockito.Mockito.mockStatic

@CompileDynamic
class AuditSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1684346670390)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1684336284765)
  static final Instant TIMESTAMP_NOW = Instant.ofEpochSecond(1607045024691)

  def 'when created with all attributes it should return the values correctly'() {
    when:
    def audit = Audit.builder()
          .createdTimestamp(CREATED_TIMESTAMP)
          .lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP)
          .build()

    then:
    audit.createdTimestamp == CREATED_TIMESTAMP
    audit.lastModifiedTimestamp == LAST_MODIFIED_TIMESTAMP
  }

  def '"now" method should set the values to the current date and time'() {
    given:
    def instantHolder = mockStatic(Instant)
    instantHolder.when(Instant::now).thenReturn(TIMESTAMP_NOW)

    when:
    def audit = Audit.now()

    then:
    audit.createdTimestamp == TIMESTAMP_NOW
    audit.lastModifiedTimestamp == TIMESTAMP_NOW
  }

  def 'attributes should be nullable'() {
    when:
    def audit = Audit.builder().build()

    then:
    audit.createdTimestamp == null
    audit.lastModifiedTimestamp == null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(Audit)

    then:
    verifier.verify()
  }
}
