package com.acroteq.ticketing.application.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;

public interface DtoToDomainMapper<DtoT extends DataTransferObject, EntityT> {

  EntityT convertDtoToDomain(DtoT entity);
}
