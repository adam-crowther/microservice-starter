package com.acroteq.ticketing.common.application.mapper;

import com.acroteq.ticketing.application.dto.Dto;

public interface ApiToDtoMapper<ApiT, DtoT extends Dto> {

  DtoT convertApiToDto(ApiT api);
}
