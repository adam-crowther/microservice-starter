package com.acroteq.ticketing.order.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.order.service.domain.dto.airline.FlightDto;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;

@Mapper(uses = { FlightIdMapper.class })
public interface FlightDtoToDomainMapper {

  Flight convertDtoToDomain(FlightDto flight);
}
