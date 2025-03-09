package com.acroteq.infrastructure.mapper;

import com.acroteq.infrastructure.data.access.entity.JpaEntity;

public interface JpaToDomainMapper<JpaT extends JpaEntity, EntityT> {

  EntityT convertJpaToDomain(JpaT entity);
}
