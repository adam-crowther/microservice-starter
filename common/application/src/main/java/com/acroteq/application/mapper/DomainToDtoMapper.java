package com.acroteq.application.mapper;

import com.acroteq.application.dto.DataTransferObject;

public interface DomainToDtoMapper<EntityT, DtoT extends DataTransferObject> {

  DtoT convertDomainToDto(EntityT entity);
}
