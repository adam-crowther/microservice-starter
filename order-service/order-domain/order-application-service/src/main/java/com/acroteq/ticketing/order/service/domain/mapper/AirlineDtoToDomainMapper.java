package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDtoToDomainMapper.class })
public interface AirlineDtoToDomainMapper {

  Airline convertDtoToDomain(AirlineDto airline);
}
