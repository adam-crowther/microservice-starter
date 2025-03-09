package com.acroteq.ticketing.domain.entity

import com.acroteq.ticketing.domain.valueobject.TestId
import groovy.transform.CompileDynamic
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

@CompileDynamic
class PrimaryEntitySpec extends Specification {

  static final TestId TEST_ID = TestId.of(987)
  static final int VERSION = 2

  def 'builder with all attributes'() {
    when:
    def primaryEntity = TestPrimaryEntity.builder()
          .id(TEST_ID)
          .version(VERSION)
          .build()
    then:
    primaryEntity.id == TEST_ID
    primaryEntity.version == VERSION
  }

  def 'builder with no attributes'() {
    when:
    def primaryEntity = TestPrimaryEntity.builder()
          .build()
    then:
    primaryEntity.id == null
    primaryEntity.version == null
  }

  def 'equals and hashcode contract is correct'() {
    when:
    def verifier = EqualsVerifier.forClass(TestPrimaryEntity)

    then:
    verifier.suppress(STRICT_INHERITANCE)
          .withOnlyTheseFields('id')
          .verify()
  }

  def 'toString returns the expected string'() {
    given:
    def entity = TestPrimaryEntity.builder()
          .id(testId)
          .version(version)
          .build()

    when:
    def string = entity.toString()

    then:
    string.startsWith('TestPrimaryEntity(')

    where:
    testId  | version
    TEST_ID | VERSION
    null    | null
  }
}
