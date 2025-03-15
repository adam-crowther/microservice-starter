package com.acroteq.ticketing.order.service.domain.mapper.airline;

import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineEventDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class })
public interface AirlineEventDtoToDomainMapper extends DtoToDomainMapper<AirlineEventDto, Airline> {

  @Override
  Airline convertDtoToDomain(AirlineEventDto airline);
}
