package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;
import org.mapstruct.MappingTarget;

public interface DomainToJpaMapper<EntityT, JpaT extends JpaEntity> {

  JpaT convertDomainToJpa(EntityT event);

  JpaT convertDomainToJpa(EntityT airline, @MappingTarget JpaT existingJpaEntity);
}
