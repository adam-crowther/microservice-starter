package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateFlightCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, DateTimeMapper.class })
public interface CreateFlightDtoToDomainMapper extends DtoToDomainMapper<CreateFlightCommandDto, Flight> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Override
  Flight convertDtoToDomain(CreateFlightCommandDto createFlightCommandDto);
}
