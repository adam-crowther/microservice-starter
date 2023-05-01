package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.event.DomainEvent;

public interface DtoToEventMapper<DtoT extends Dto, EventT extends DomainEvent<?>> {

  EventT convertDtoToEvent(DtoT entity);
}
