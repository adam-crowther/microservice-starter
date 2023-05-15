package com.acroteq.ticketing.infrastructure.data.access.resolver;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;

public interface JpaResolver<IdT extends EntityId, JpaT extends JpaEntity> {

  JpaT resolve(IdT customerId);
}
