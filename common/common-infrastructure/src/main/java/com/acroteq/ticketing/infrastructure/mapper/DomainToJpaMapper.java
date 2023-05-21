package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.entity.Entity;
import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;
import org.mapstruct.MappingTarget;

public interface DomainToJpaMapper<EntityT extends Entity<?>, JpaT extends JpaEntity> {

  JpaT convertDomainToJpa(EntityT entity);

  JpaT convertDomainToJpa(EntityT entity, @MappingTarget JpaT existingJpaEntity);
}
