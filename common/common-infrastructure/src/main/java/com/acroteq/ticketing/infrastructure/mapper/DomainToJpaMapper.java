package com.acroteq.ticketing.infrastructure.mapper;

public interface DomainToJpaMapper<EntityT, JpaT> {

  JpaT convertDomainToJpa(EntityT event);
}
