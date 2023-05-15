package com.acroteq.ticketing.domain.entity

import com.acroteq.ticketing.domain.valueobject.Audit
import com.acroteq.ticketing.domain.valueobject.TestId
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import java.time.Instant

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class AggregateRootSpec extends Specification {

  static final Instant CREATED_TIMESTAMP = Instant.ofEpochSecond(1683832517)
  static final Instant LAST_MODIFIED_TIMESTAMP = Instant.ofEpochSecond(1678344706)

  static final TestId TEST_ID = TestId.of(987)
  static final int VERSION = 2
  static final Audit AUDIT = Audit.builder().createdTimestamp(CREATED_TIMESTAMP).lastModifiedTimestamp(LAST_MODIFIED_TIMESTAMP).build()

  def "builder with all attributes"() {
    when:
      def aggregateRoot = TestAggregateRoot.builder()
            .id(TEST_ID)
            .version(VERSION)
            .audit(AUDIT)
            .build()
    then:
      aggregateRoot.getId() == TEST_ID
      aggregateRoot.getVersion() == VERSION
      aggregateRoot.getAudit() == AUDIT
  }

  def "builder with no attributes"() {
    when:
      def aggregateRoot = TestAggregateRoot.builder()
            .build()
    then:
      aggregateRoot.getId() == null
      aggregateRoot.getVersion() == null
      aggregateRoot.getAudit() != null
      aggregateRoot.getAudit().getLastModifiedTimestamp() == null
      aggregateRoot.getAudit().getCreatedTimestamp() == null
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(TestAggregateRoot)

    then:
      verifier.suppress(STRICT_INHERITANCE)
            .withIgnoredFields("version", "audit")
            .verify()
  }

  def "toString returns the expected string"() {
    given:
      def dto = TestAggregateRoot.builder()
            .id(testId)
            .version(version)
            .audit(audit)
            .build()

    when:
      def string = dto.toString()

    then:
      string == expected

    where:
      testId  | version | audit | expected
      TEST_ID | VERSION | AUDIT | "TestAggregateRoot(super=AggregateRoot(super=MasterEntity(id=TestId(super=987), version=2), audit=Audit(createdTimestamp=2023-05-11T19:15:17Z, lastModifiedTimestamp=2023-03-09T06:51:46Z)))"
      null    | null    | null  | "TestAggregateRoot(super=AggregateRoot(super=MasterEntity(id=null, version=null), audit=null))"
  }
}
