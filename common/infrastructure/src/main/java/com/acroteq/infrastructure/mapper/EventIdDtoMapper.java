package com.acroteq.infrastructure.mapper;

import com.acroteq.application.dto.EventIdDto;
import com.acroteq.application.mapper.MapstructConfig;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class)
public class EventIdDtoMapper {

  public EventIdDto convert(final Integer partition, final Long offset) {
    return EventIdDto.builder()
                     .partition(partition)
                     .offset(offset)
                     .build();
  }
}
