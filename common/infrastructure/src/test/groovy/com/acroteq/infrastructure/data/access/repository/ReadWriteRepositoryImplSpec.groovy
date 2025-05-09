package com.acroteq.infrastructure.data.access.repository

import com.acroteq.infrastructure.data.access.entity.TestEntity
import com.acroteq.infrastructure.data.access.entity.TestJpaEntity
import com.acroteq.infrastructure.data.access.jpa.TestJpaRepository
import com.acroteq.infrastructure.data.access.mapper.TestJpaMapper
import com.acroteq.infrastructure.data.access.valueobject.TestId
import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class ReadWriteRepositoryImplSpec extends Specification {

  static final Long ID = 123
  static final TestId TEST_ID = TestId.of(ID)

  TestJpaRepository jpaRepository = Mock()
  TestJpaMapper jpaMapper = Mock()

  ReadWriteRepositoryImpl<TestId, TestEntity, TestJpaEntity> repository = new ReadWriteRepositoryImpl<>(jpaRepository, jpaMapper)

  def 'loadAll returns a list containing all the existing entities'() {
    given:
    def testEntity = Mock(TestEntity)
    def testJpaEntity = Mock(TestJpaEntity)

    jpaRepository.findAll() >> List.of(testJpaEntity)
    jpaMapper.convert(testJpaEntity) >> testEntity

    when:
    def entities = repository.loadAll()

    then:
    entities == List.of(testEntity)
  }

  def 'findById should return an optional containing the requested entity'() {
    given:
    def testEntity = Mock(TestEntity)
    def testJpaEntity = Mock(TestJpaEntity)

    jpaRepository.findById(ID) >> Optional.ofNullable(testJpaEntity)
    jpaMapper.convert(testJpaEntity) >> testEntity

    when:
    def entity = repository.findById(TEST_ID)

    then:
    entity == Optional.of(testEntity)
  }

  def 'findById returns an optional containing the requested entity'() {
    given:
    jpaRepository.findById(ID) >> Optional.empty()

    when:
    def entity = repository.findById(TEST_ID)

    then:
    entity == Optional.empty()
  }

  def 'findById throws an exception if the given id is null'() {
    when:
    repository.findById(null)

    then:
    thrown(NullPointerException)
  }

  def 'existsById returns true if the entity exists'() {
    given:
    jpaRepository.existsById(ID) >> true

    when:
    def exists = repository.existsById(TEST_ID)

    then:
    exists
  }

  def 'existsById returns false if the entity does not exist'() {
    given:
    jpaRepository.existsById(ID) >> false

    when:
    def exists = repository.existsById(TEST_ID)

    then:
    !exists
  }

  def 'existsById throws an exception if the given id is null'() {
    when:
    repository.existsById(null)

    then:
    thrown(NullPointerException)
  }

  def 'save should convert to a new jpa entity and insert it'() {
    given:
    def newEntity = Mock(TestEntity)
    def savedEntity = Mock(TestEntity)
    def newJpaEntity = Mock(TestJpaEntity)
    def savedJpaEntity = Mock(TestJpaEntity)

    1 * jpaMapper.convertNewOrExisting(newEntity) >> newJpaEntity
    1 * jpaRepository.saveAndFlush(newJpaEntity) >> savedJpaEntity
    1 * jpaMapper.convert(savedJpaEntity) >> savedEntity
    when:
    def saved = repository.save(newEntity)
    then:
    saved == savedEntity
  }

  def 'save throws an exception if the given entity is null'() {
    when:
    repository.save(null)
    then:
    thrown(NullPointerException)
  }

  def 'deleteById simply delegates to the jpa repository'() {
    when:
    repository.deleteById(TEST_ID)

    then:
    1 * jpaRepository.deleteById(ID)
  }

  def 'deleteById throws an exception if the given id is null'() {
    when:
    repository.deleteById(null)

    then:
    thrown(NullPointerException)
  }
}
