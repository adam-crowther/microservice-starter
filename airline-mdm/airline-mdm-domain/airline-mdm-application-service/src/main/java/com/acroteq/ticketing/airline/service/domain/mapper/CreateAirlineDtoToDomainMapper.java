package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, CreateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface CreateAirlineDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  Airline convertDtoToDomain(CreateAirlineCommandDto createAirlineCommandDto);
}
