package com.acroteq.ticketing.domain.entity

import com.acroteq.ticketing.domain.valueobject.TestId
import nl.jqno.equalsverifier.EqualsVerifier
import spock.lang.Specification

import static nl.jqno.equalsverifier.Warning.STRICT_INHERITANCE

class MasterEntitySpec extends Specification {

  static final TestId TEST_ID = TestId.of(987)
  static final int VERSION = 2

  def "builder with all attributes"() {
    when:
      def masterEntity = TestMasterEntity.builder()
            .id(TEST_ID)
            .version(VERSION)
            .build()
    then:
      masterEntity.getId() == TEST_ID
      masterEntity.getVersion() == VERSION
  }

  def "builder with no attributes"() {
    when:
      def masterEntity = TestMasterEntity.builder()
            .build()
    then:
      masterEntity.getId() == null
      masterEntity.getVersion() == null
  }

  def "equals and hashcode contract is correct"() {
    when:
      def verifier = EqualsVerifier.forClass(TestMasterEntity)

    then:
      verifier.suppress(STRICT_INHERITANCE)
            .withIgnoredFields("version")
            .verify()
  }

  def "toString returns the expected string"() {
    given:
      def dto = TestMasterEntity.builder()
            .id(testId)
            .version(version)
            .build()

    when:
      def string = dto.toString()

    then:
      string == expected

    where:
      testId  | version | expected
      TEST_ID | VERSION | "TestMasterEntity(super=MasterEntity(id=TestId(super=987), version=2))"
      null    | null    | "TestMasterEntity(super=MasterEntity(id=null, version=null))"
  }
}
