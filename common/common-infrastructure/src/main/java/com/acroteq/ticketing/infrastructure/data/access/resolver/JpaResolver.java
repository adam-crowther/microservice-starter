package com.acroteq.ticketing.infrastructure.data.access.resolver;

import com.acroteq.ticketing.domain.valueobject.BaseId;
import com.acroteq.ticketing.infrastructure.data.access.entity.JpaEntity;

public interface JpaResolver<IdT extends BaseId, JpaT extends JpaEntity> {

  JpaT resolve(IdT customerId);
}
