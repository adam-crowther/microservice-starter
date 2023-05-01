package com.acroteq.ticketing.order.service.domain.mapper.airline;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineCreatedDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class })
public interface AirlineCreatedDtoToDomainMapper extends DtoToDomainMapper<AirlineCreatedDto, Airline> {

  @Override
  Airline convertDtoToDomain(AirlineCreatedDto airline);
}
