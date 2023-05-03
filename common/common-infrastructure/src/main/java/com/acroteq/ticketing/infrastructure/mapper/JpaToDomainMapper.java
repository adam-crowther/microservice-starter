package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;

public interface JpaToDomainMapper<JpaT extends JpaEntity, EntityT> {

  EntityT convertJpaToDomain(JpaT event);
}
