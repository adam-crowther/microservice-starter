package com.acroteq.application.resolver;

import com.acroteq.domain.valueobject.EntityId;

public interface Resolver<IdT extends EntityId, TypeT> {

  TypeT resolve(Long id);

  TypeT resolve(IdT id);

  TypeT resolve(String key);

  TypeT resolveOptional(Long id);

  TypeT resolveOptional(IdT id);

  TypeT resolveOptional(String key);
}
