package com.acroteq.ticketing.application.resolver;

import com.acroteq.ticketing.domain.valueobject.BaseId;

public interface Resolver<IdT extends BaseId, TypeT> {

  TypeT resolve(IdT id);

  TypeT resolve(Long id);
}
