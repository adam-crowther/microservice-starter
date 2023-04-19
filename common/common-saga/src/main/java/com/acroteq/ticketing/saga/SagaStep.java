package com.acroteq.ticketing.saga;

import com.acroteq.ticketing.domain.event.DomainEvent;

public interface SagaStep<T, S extends DomainEvent<?>, U extends DomainEvent<?>> {

  S process(T data);

  U rollback(T data);
}
