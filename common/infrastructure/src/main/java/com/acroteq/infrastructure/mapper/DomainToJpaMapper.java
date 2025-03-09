package com.acroteq.infrastructure.mapper;

import com.acroteq.domain.entity.Entity;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;
import org.mapstruct.MappingTarget;

public interface DomainToJpaMapper<EntityT extends Entity<?>, JpaT extends JpaEntity> {

  JpaT convertDomainToJpa(EntityT entity);

  JpaT convertDomainToJpa(EntityT entity, @MappingTarget JpaT existingJpaEntity);
}
