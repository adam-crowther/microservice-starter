package com.acroteq.ticketing.infrastructure.mapper;

public interface JpaToDomainMapper<JpaT, EntityT> {

  EntityT convertJpaToDomain(JpaT event);
}
