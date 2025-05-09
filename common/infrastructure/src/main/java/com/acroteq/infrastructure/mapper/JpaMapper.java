package com.acroteq.infrastructure.mapper;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.exception.EntityNotFoundException;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;
import com.acroteq.mapstruct.Existing;
import com.acroteq.mapstruct.New;
import com.acroteq.mapstruct.NewOrExisting;
import com.acroteq.mapstruct.Resolve;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.mapstruct.MappingTarget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public interface JpaMapper<EntityT extends Entity<? extends EntityId>, JpaT extends JpaEntity> {

  EntityT convert(JpaT entity);

  @Nullable
  @Resolve
  default JpaT resolve(@Nullable final EntityT entity) {
    return Optional.ofNullable(entity)
                   .map(Entity::getId)
                   .map(this::findById)
                   .orElse(null);
  }

  @NonNull
  default JpaT findById(@NonNull final EntityId id) {
    final JpaRepository<JpaT, Long> repository = getRepository();
    return repository.findById(id.getValue())
                     .orElseThrow(() -> new EntityNotFoundException(id));

  }

  JpaRepository<JpaT, Long> getRepository();

  @NonNull
  @NewOrExisting
  default JpaT convertNewOrExisting(@NonNull final EntityT entity) {
    final JpaRepository<JpaT, Long> repository = getRepository();
    return Optional.of(entity)
                   .map(Entity::getId)
                   .map(EntityId::getValue)
                   .flatMap(repository::findById)
                   .map(convertExisting(entity))
                   .orElseGet(() -> convertNew(entity));
  }

  @New
  JpaT convertNew(EntityT entity);

  @Existing
  JpaT convertExisting(EntityT entity, @MappingTarget JpaT existingJpaEntity);

  @NonNull
  default Function<JpaT, JpaT> convertExisting(@NonNull final EntityT entity) {
    return existing -> convertExisting(entity, existing);
  }
}
