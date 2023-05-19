package com.acroteq.ticketing.order.service.domain.mapper.airline;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.airline.FlightDto;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class })
public interface FlightDtoToDomainMapper extends DtoToDomainMapper<FlightDto, Flight> {

  @Override
  Flight convertDtoToDomain(FlightDto flight);
}
