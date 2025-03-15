package com.acroteq.domain.entity

import com.acroteq.domain.valueobject.EventId
import com.acroteq.domain.valueobject.TestId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class ReplicatedEntitySpec extends Specification {

  static final int PARTITION = 2
  static final long OFFSET = 54352

  static final TestId TEST_ID = TestId.of(987)
  static final int VERSION = 2
  static final EventId EVENT_ID = EventId.builder()
        .partition(PARTITION)
        .offset(OFFSET)
        .build()

  def 'builder with all attributes'() {
    when:
    def replicatedEntity = TestReplicatedEntity.builder()
          .id(TEST_ID)
          .version(VERSION)
          .eventId(EVENT_ID)
          .build()

    then:
    replicatedEntity.id == TEST_ID
    replicatedEntity.version == VERSION
    replicatedEntity.eventId == EVENT_ID
  }

  def 'builder with missing attributes'() {
    given:
    def builder = TestReplicatedEntity.builder()
    Optional.ofNullable(id).ifPresent { builder::id }
    Optional.ofNullable(version).ifPresent { builder::version }
    Optional.ofNullable(eventId).ifPresent { builder::eventId }

    when:
    builder.build()

    then:
    thrown(NullPointerException)

    where:
    id      | version | eventId
    null    | VERSION | EVENT_ID
    TEST_ID | null    | EVENT_ID
    TEST_ID | VERSION | null
  }

  def 'builder with null attributes'() {
    when:
    TestReplicatedEntity.builder()
          .id(id)
          .version(version)
          .eventId(eventId)
          .build()

    then:
    thrown(NullPointerException)

    where:
    id      | version | eventId
    null    | VERSION | EVENT_ID
    TEST_ID | null    | EVENT_ID
    TEST_ID | VERSION | null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(TestReplicatedEntity)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .withNonnullFields('id', 'version', 'eventId')
          .withOnlyTheseFields('id')
          .verify()
  }

  def 'toString returns the expected string'() {
    given:
    def entity = TestReplicatedEntity.builder()
          .id(TEST_ID)
          .version(VERSION)
          .eventId(EVENT_ID)
          .build()

    when:
    def string = entity.toString()

    then:
    string.startsWith('TestReplicatedEntity(')
  }

  def 'isFromTheSameEventAs should return true if both partition and offset are equal, otherwise false'() {
    given:
    def entity = TestReplicatedEntity.builder()
          .id(TEST_ID)
          .version(VERSION)
          .eventId(EVENT_ID)
          .build()

    def otherEventId = EventId.builder()
          .partition(otherPartition)
          .offset(otherOffset)
          .build()
    def otherId = TestId.of(654)
    def otherVersion = 1
    def otherEntity = TestReplicatedEntity.builder()
          .id(otherId)
          .version(otherVersion)
          .eventId(otherEventId)
          .build()

    when:
    def isSame = entity.isFromTheSameEventAs(otherEntity)

    then:
    isSame == expected

    where:
    otherPartition | otherOffset || expected
    PARTITION      | OFFSET      || true
    PARTITION + 1  | OFFSET      || false
    PARTITION - 1  | OFFSET      || false
    PARTITION      | OFFSET + 1  || false
    PARTITION      | OFFSET - 1  || false
  }

  def 'isFromAnEarlierEventThan should return true if the partition is the same and the offset is lower, otherwise false'() {
    given:
    def entity = TestReplicatedEntity.builder()
          .id(TEST_ID)
          .version(VERSION)
          .eventId(EVENT_ID)
          .build()

    def otherEventId = EventId.builder()
          .partition(otherPartition)
          .offset(otherOffset)
          .build()
    def otherId = TestId.of(654)
    def otherVersion = 1
    def otherEntity = TestReplicatedEntity.builder()
          .id(otherId)
          .version(otherVersion)
          .eventId(otherEventId)
          .build()

    when:
    def isSame = entity.isFromAnEarlierEventThan(otherEntity)

    then:
    isSame == expected

    where:
    otherPartition | otherOffset || expected
    PARTITION      | OFFSET      || false
    PARTITION + 1  | OFFSET      || false
    PARTITION - 1  | OFFSET      || false
    PARTITION      | OFFSET + 1  || true
    PARTITION      | OFFSET - 1  || false
  }
}
