package com.acroteq.ticketing.common.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;

public interface DtoToApiMapper<DtoT extends Dto, ApiT> {

  ApiT convertDtoToApi(DtoT dto);
}
