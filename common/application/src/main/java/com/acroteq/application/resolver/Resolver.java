package com.acroteq.application.resolver;

import com.acroteq.domain.valueobject.EntityId;

public interface Resolver<IdT extends EntityId, TypeT> {

  TypeT resolve(IdT id);

  TypeT resolve(Long id);
}
