package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineDeletedDto;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = AirlineIdMapper.class)
public interface AirlineDeletedDtoToDomainMapper extends DtoToDomainMapper<AirlineDeletedDto, AirlineId> {

  @Mapping(target = "value", source = "id")
  @Override
  AirlineId convertDtoToDomain(AirlineDeletedDto dto);
}
