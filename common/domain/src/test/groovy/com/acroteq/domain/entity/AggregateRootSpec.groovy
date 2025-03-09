package com.acroteq.ticketing.domain.entity

import com.acroteq.domain.valueobject.Audit
import com.acroteq.ticketing.domain.valueobject.TestId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class AggregateRootSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1683832517)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1678344706)

  static final TestId TEST_ID = TestId.of(987)
  static final int VERSION = 2
  static final Audit AUDIT = Audit.builder().createdTimestamp(CREATED_TIMESTAMP).lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP).build()

  def 'builder with all attributes'() {
    when:
    def aggregateRoot = TestAggregateRoot.builder()
          .id(TEST_ID)
          .version(VERSION)
          .audit(AUDIT)
          .build()
    then:
    aggregateRoot.id == TEST_ID
    aggregateRoot.version == VERSION
    aggregateRoot.audit == AUDIT
  }

  def 'builder with no attributes'() {
    when:
    def aggregateRoot = TestAggregateRoot.builder()
          .build()
    then:
    aggregateRoot.id == null
    aggregateRoot.version == null
    aggregateRoot.audit != null
    aggregateRoot.audit.lastModifiedTimestamp == null
    aggregateRoot.audit.createdTimestamp == null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(TestAggregateRoot)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .withOnlyTheseFields('id')
          .verify()
  }

  def 'toString returns the expected string'() {
    given:
    def entity = TestAggregateRoot.builder()
          .id(testId)
          .version(version)
          .audit(audit)
          .build()

    when:
    def string = entity.toString()

    then:
    string.startsWith('TestAggregateRoot(')

    where:
    testId  | version | audit
    TEST_ID | VERSION | AUDIT
    null    | null    | null
  }
}
