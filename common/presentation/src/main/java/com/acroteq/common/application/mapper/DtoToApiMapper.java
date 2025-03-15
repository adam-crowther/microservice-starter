package com.acroteq.common.application.mapper;

import com.acroteq.application.dto.DataTransferObject;

public interface DtoToApiMapper<DtoT extends DataTransferObject, ApiT> {

  ApiT convertDtoToApi(DtoT dto);
}
