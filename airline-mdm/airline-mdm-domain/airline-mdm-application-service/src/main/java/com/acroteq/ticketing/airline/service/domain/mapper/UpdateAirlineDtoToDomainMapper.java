package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, UpdateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface UpdateAirlineDtoToDomainMapper {

  Airline convertDtoToDomain(UpdateAirlineCommandDto dto);
}
