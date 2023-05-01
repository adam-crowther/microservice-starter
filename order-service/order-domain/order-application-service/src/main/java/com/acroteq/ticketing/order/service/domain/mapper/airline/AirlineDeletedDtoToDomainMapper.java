package com.acroteq.ticketing.order.service.domain.mapper.airline;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.domain.valueobject.AirlineId;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDeletedDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = AirlineIdMapper.class)
public interface AirlineDeletedDtoToDomainMapper extends DtoToDomainMapper<AirlineDeletedDto, AirlineId> {

  @Mapping(target = "value", source = "id")
  @Override
  AirlineId convertDtoToDomain(AirlineDeletedDto airline);
}
