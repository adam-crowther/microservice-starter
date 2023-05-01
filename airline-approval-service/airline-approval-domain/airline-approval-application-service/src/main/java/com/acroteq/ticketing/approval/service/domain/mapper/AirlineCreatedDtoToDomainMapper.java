package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineCreatedDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface AirlineCreatedDtoToDomainMapper extends DtoToDomainMapper<AirlineCreatedDto, Airline> {

  @Override
  Airline convertDtoToDomain(AirlineCreatedDto dto);
}
