package com.acroteq.infrastructure.data.access.repository;

import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class WriteRepositoryImpl<IdT extends EntityId, EntityT extends Entity<IdT>, JpaT extends JpaEntity>
    implements WriteRepository<IdT, EntityT> {

  private final JpaRepository<JpaT, Long> jpaRepository;
  private final JpaToDomainMapper<JpaT, EntityT> jpaToDomainMapper;
  private final DomainToJpaMapper<EntityT, JpaT> domainToJpaMapper;

  @Override
  public EntityT save(@NonNull final EntityT entity) {

    return Optional.of(entity)
                   .map(EntityT::getId)
                   .map(IdT::getValue)
                   .flatMap(jpaRepository::findById)
                   .map(updateExistingEntity(entity))
                   .orElseGet(insertNewEntity(entity));
  }

  private Function<JpaT, EntityT> updateExistingEntity(final EntityT entity) {
    return existingEntity -> updateExistingEntity(existingEntity, entity);
  }

  private EntityT updateExistingEntity(final JpaT existingEntity, final EntityT entity) {
    final JpaT updatedEntity = domainToJpaMapper.convertDomainToJpa(entity, existingEntity);
    // We use saveAndFlush so that the response will be populated with the audit data - eg createdTimestamp,
    // createdBy, etc.
    final JpaT savedEntity = jpaRepository.saveAndFlush(updatedEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  private Supplier<EntityT> insertNewEntity(final EntityT entity) {
    return () -> insertEntity(entity);
  }

  private EntityT insertEntity(final EntityT entity) {
    final JpaT newEntity = domainToJpaMapper.convertDomainToJpa(entity);
    // We use saveAndFlush so that the response will be populated with the audit data - eg createdTimestamp,
    // createdBy, etc.
    final JpaT savedEntity = jpaRepository.saveAndFlush(newEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  @Override
  public void deleteById(@NonNull final IdT id) {
    jpaRepository.deleteById(id.getValue());
  }
}
