package com.acroteq.application.mapper;

import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.ticketing.domain.event.Event;

public interface DtoToEventMapper<DtoT extends DataTransferObject, EventT extends Event> {

  EventT convertDtoToEvent(DtoT entity);
}
