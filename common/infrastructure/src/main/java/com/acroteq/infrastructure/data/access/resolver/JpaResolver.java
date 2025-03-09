package com.acroteq.infrastructure.data.access.resolver;

import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.infrastructure.data.access.entity.JpaEntity;

public interface JpaResolver<IdT extends EntityId, JpaT extends JpaEntity> {

  JpaT resolve(IdT customerId);
}
