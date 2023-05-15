package com.acroteq.ticketing.infrastructure.data.access.repository

import com.acroteq.ticketing.infrastructure.data.access.entity.TestEntity
import com.acroteq.ticketing.infrastructure.data.access.entity.TestJpaEntity
import com.acroteq.ticketing.infrastructure.data.access.jpa.TestJpaRepository
import com.acroteq.ticketing.infrastructure.data.access.mapper.TestJpaToDomainMapper
import com.acroteq.ticketing.infrastructure.data.access.valueobject.TestId
import spock.lang.Specification

class ReadRepositoryImplSpec extends Specification {

  static final Long ID = 123
  static final TestId TEST_ID = TestId.of(ID)

  TestJpaRepository jpaRepository = Mock()
  TestJpaToDomainMapper jpaToDomainMapper = Mock()

  ReadRepositoryImpl repository = new ReadRepositoryImpl(jpaRepository, jpaToDomainMapper)

  def "findById returns an optional containing the requested entity"() {
    given:
      def testEntity = Mock(TestEntity)
      def testJpaEntity = Mock(TestJpaEntity)

      jpaRepository.findById(ID) >> Optional.ofNullable(testJpaEntity)
      jpaToDomainMapper.convertJpaToDomain(testJpaEntity) >> testEntity

    when:
      def entity = repository.findById(TEST_ID)

    then:
      entity == Optional.of(testEntity)
  }

  def "findById returns an optional containing the requested entity"() {
    given:
      jpaRepository.findById(ID) >> Optional.empty()

    when:
      def entity = repository.findById(TEST_ID)

    then:
      entity == Optional.empty()
  }

  def "findById throws an exception if the given id is null"() {
    when:
      repository.findById(null)

    then:
      thrown(NullPointerException)
  }

  def "existsById returns true if the entity exists"() {
    given:
      jpaRepository.existsById(ID) >> true

    when:
      def exists = repository.existsById(TEST_ID)

    then:
      exists
  }

  def "existsById returns false if the entity does not exist"() {
    given:
      jpaRepository.existsById(ID) >> false

    when:
      def exists = repository.existsById(TEST_ID)

    then:
      !exists
  }

  def "existsById throws an exception if the given id is null"() {
    when:
      repository.existsById(null)

    then:
      thrown(NullPointerException)
  }
}