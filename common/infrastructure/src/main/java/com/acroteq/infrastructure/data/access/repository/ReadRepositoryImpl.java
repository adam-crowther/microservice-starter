package com.acroteq.infrastructure.data.access.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.infrastructure.mapper.JpaMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReadRepositoryImpl<IdT extends EntityId, EntityT extends Entity<IdT>, JpaT extends JpaEntity>
    implements ReadRepository<IdT, EntityT> {

  private final JpaRepository<JpaT, Long> jpaRepository;
  private final JpaMapper<EntityT, JpaT> jpaMapper;

  @Override
  public List<EntityT> loadAll() {
    return jpaRepository.findAll()
                        .stream()
                        .map(jpaMapper::convert)
                        .toList();
  }

  @Override
  public Optional<EntityT> findById(@NonNull final IdT entityId) {
    return jpaRepository.findById(entityId.getValue())
                        .map(jpaMapper::convert);
  }

  @Override
  public boolean existsById(@NonNull final IdT entityId) {
    return jpaRepository.existsById(entityId.getValue());
  }
}
