package com.acroteq.infrastructure.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.infrastructure.data.access.entity.EventIdJpaEmbedded;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public interface EventIdJpaMapper {

  EventIdJpaEmbedded convert(EventId eventId);
  
}
