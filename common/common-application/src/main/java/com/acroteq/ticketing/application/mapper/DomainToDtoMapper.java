package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;

public interface DomainToDtoMapper<EntityT, DtoT extends DataTransferObject> {

  DtoT convertDomainToDto(EntityT entity);
}
