package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.event.Event;

public interface EventToDtoMapper<EventT extends Event, DtoT extends DataTransferObject> {

  DtoT convertEventToDto(EventT event);
}
