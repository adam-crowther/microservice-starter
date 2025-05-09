package com.acroteq.infrastructure.data.access.repository;

import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.infrastructure.mapper.JpaMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class WriteRepositoryImpl<IdT extends EntityId, EntityT extends Entity<IdT>, JpaT extends JpaEntity>
    implements WriteRepository<IdT, EntityT> {

  private final JpaRepository<JpaT, Long> jpaRepository;
  private final JpaMapper<EntityT, JpaT> jpaMapper;

  @Override
  public EntityT save(@NonNull final EntityT entity) {

    final JpaT jpaEntity = jpaMapper.convertNewOrExisting(entity);
    final JpaT savedEntity = jpaRepository.saveAndFlush(jpaEntity);
    return jpaMapper.convert(savedEntity);
  }

  @Override
  public void deleteById(@NonNull final IdT id) {
    jpaRepository.deleteById(id.getValue());
  }
}
