package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineUpdatedDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface AirlineUpdatedDtoToDomainMapper extends DtoToDomainMapper<AirlineUpdatedDto, Airline> {

  @Override
  Airline convertDtoToDomain(AirlineUpdatedDto dto);
}
