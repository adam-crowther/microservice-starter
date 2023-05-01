package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;

public interface DtoToDomainMapper<DtoT extends Dto, EntityT> {

  EntityT convertDtoToDomain(DtoT entity);
}
