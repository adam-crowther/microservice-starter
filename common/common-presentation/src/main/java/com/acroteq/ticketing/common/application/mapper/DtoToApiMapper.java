package com.acroteq.ticketing.common.application.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;

public interface DtoToApiMapper<DtoT extends DataTransferObject, ApiT> {

  ApiT convertDtoToApi(DtoT dto);
}
