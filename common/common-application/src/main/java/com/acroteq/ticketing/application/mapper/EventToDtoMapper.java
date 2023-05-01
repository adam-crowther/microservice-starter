package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.domain.event.DomainEvent;

public interface EventToDtoMapper<EventT extends DomainEvent<?>, DtoT extends Dto> {

  DtoT convertEventToDto(EventT event);
}
