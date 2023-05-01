package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;

public interface DomainToDtoMapper<EntityT, DtoT extends Dto> {

  DtoT convertDomainToDto(EntityT entity);
}
