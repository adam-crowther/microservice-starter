package com.acroteq.application.resolver;

import com.acroteq.ticketing.domain.valueobject.EntityId;

public interface Resolver<IdT extends EntityId, TypeT> {

  TypeT resolve(IdT id);

  TypeT resolve(Long id);
}
