package com.acroteq.ticketing.infrastructure.data.access.repository

import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl
import com.acroteq.ticketing.infrastructure.data.access.entity.TestEntity
import com.acroteq.ticketing.infrastructure.data.access.entity.TestJpaEntity
import com.acroteq.ticketing.infrastructure.data.access.jpa.TestJpaRepository
import com.acroteq.ticketing.infrastructure.data.access.mapper.TestDomainToJpaMapper
import com.acroteq.ticketing.infrastructure.data.access.mapper.TestJpaToDomainMapper
import com.acroteq.ticketing.infrastructure.data.access.valueobject.TestId
import groovy.transform.CompileDynamic
import spock.lang.Specification

@CompileDynamic
class ReadWriteRepositoryImplSpec extends Specification {

  static final Long ID = 123
  static final TestId TEST_ID = TestId.of(ID)

  TestJpaRepository jpaRepository = Mock()
  TestJpaToDomainMapper jpaToDomainMapper = Mock()
  TestDomainToJpaMapper domainToJpaMapper = Mock()

  ReadWriteRepositoryImpl<TestId, TestEntity, TestJpaEntity> repository = new ReadWriteRepositoryImpl<>(jpaRepository, jpaToDomainMapper, domainToJpaMapper)

  def 'findById should return an optional containing the requested entity'() {
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

  def 'when the entity has no id and does not already exist, save should convert to a new jpa entity and insert it'() {
    given:
    def newEntity = Mock(TestEntity)
    def savedEntity = Mock(TestEntity)
    def newJpaEntity = Mock(TestJpaEntity)
    def savedJpaEntity = Mock(TestJpaEntity)

    1 * newEntity.id >> null
    0 * jpaRepository.findById(ID)
    1 * domainToJpaMapper.convertDomainToJpa(newEntity) >> newJpaEntity
    1 * jpaRepository.saveAndFlush(newJpaEntity) >> savedJpaEntity
    1 * jpaToDomainMapper.convertJpaToDomain(savedJpaEntity) >> savedEntity
    when:
    def saved = repository.save(newEntity)
    then:
    saved == savedEntity
  }

  def 'when the entity has an id but does not already exist, save should convert to a new jpa entity and insert it'() {
    given:
    def newEntity = Mock(TestEntity)
    def savedEntity = Mock(TestEntity)
    def newJpaEntity = Mock(TestJpaEntity)
    def savedJpaEntity = Mock(TestJpaEntity)

    1 * newEntity.id >> TEST_ID
    1 * jpaRepository.findById(ID) >> Optional.empty()
    1 * domainToJpaMapper.convertDomainToJpa(newEntity) >> newJpaEntity
    1 * jpaRepository.saveAndFlush(newJpaEntity) >> savedJpaEntity
    1 * jpaToDomainMapper.convertJpaToDomain(savedJpaEntity) >> savedEntity
    when:
    def saved = repository.save(newEntity)
    then:
    saved == savedEntity
  }

  def 'when the entity already exists, save should get the reference to the existing entity and update it'() {
    given:
    def existingEntity = Mock(TestEntity)
    def savedEntity = Mock(TestEntity)
    def existingJpaEntity = Mock(TestJpaEntity)
    def savedJpaEntity = Mock(TestJpaEntity)

    1 * existingEntity.id >> TEST_ID
    1 * jpaRepository.findById(ID) >> Optional.of(existingJpaEntity)
    1 * domainToJpaMapper.convertDomainToJpa(existingEntity, existingJpaEntity) >> existingJpaEntity
    1 * jpaRepository.saveAndFlush(existingJpaEntity) >> savedJpaEntity
    1 * jpaToDomainMapper.convertJpaToDomain(savedJpaEntity) >> savedEntity
    when:
    def saved = repository.save(existingEntity)
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
