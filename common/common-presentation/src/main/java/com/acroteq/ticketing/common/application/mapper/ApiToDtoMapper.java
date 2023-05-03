package com.acroteq.ticketing.common.application.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;

public interface ApiToDtoMapper<ApiT, DtoT extends DataTransferObject> {

  DtoT convertApiToDto(ApiT api);
}
