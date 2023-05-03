package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.application.dto.EventIdDto;
import org.mapstruct.Mapper;

@Mapper
public class EventIdMessageToDtoMapper {

  public EventIdDto convertMessageToDto(final Integer partition, final Long offset) {
    return EventIdDto.builder()
                     .partition(partition)
                     .offset(offset)
                     .build();
  }
}
