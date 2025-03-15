package com.acroteq.common.application.mapper;

import com.acroteq.application.dto.DataTransferObject;

public interface ApiToDtoMapper<ApiT, DtoT extends DataTransferObject> {

  DtoT convertApiToDto(ApiT api);
}
