package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineEventDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface AirlineEventDtoToDomainMapper extends DtoToDomainMapper<AirlineEventDto, Airline> {

  @Override
  Airline convertDtoToDomain(AirlineEventDto dto);
}
