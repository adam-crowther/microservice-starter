package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.valueobject.EventId;
import com.acroteq.ticketing.infrastructure.data.access.entity.EventIdJpaEmbedded;
import org.mapstruct.Mapper;

@Mapper
public interface EventIdDomainToJpaMapper {

  EventIdJpaEmbedded convertMessageToDto(EventId eventId);
}
