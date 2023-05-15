package com.acroteq.ticketing.infrastructure.data.access.repository;

import com.acroteq.ticketing.application.repository.ReadRepository;
import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.domain.valueobject.EntityId;
import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class ReadRepositoryImpl<IdT extends EntityId, EntityT extends Entity<IdT>, JpaT extends JpaEntity>
    implements ReadRepository<IdT, EntityT> {

  private final JpaRepository<JpaT, Long> jpaRepository;
  private final JpaToDomainMapper<JpaT, EntityT> jpaToDomainMapper;

  @Override
  public Optional<EntityT> findById(@NonNull final IdT entityId) {
    return jpaRepository.findById(entityId.getValue())
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public boolean existsById(@NonNull final IdT entityId) {
    return jpaRepository.existsById(entityId.getValue());
  }
}
