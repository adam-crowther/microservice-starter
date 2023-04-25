package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateFlightCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, DateTimeMapper.class })
public interface CreateFlightDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  Flight convertDtoToDomain(CreateFlightCommandDto createFlightCommandDto);
}
