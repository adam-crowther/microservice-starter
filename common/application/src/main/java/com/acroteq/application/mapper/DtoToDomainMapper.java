package com.acroteq.application.mapper;

import com.acroteq.application.dto.DataTransferObject;

public interface DtoToDomainMapper<DtoT extends DataTransferObject, EntityT> {

  EntityT convertDtoToDomain(DtoT entity);
}
