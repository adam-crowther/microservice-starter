package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, CreateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface CreateAirlineDtoToDomainMapper extends DtoToDomainMapper<CreateAirlineCommandDto, Airline> {

  @Mapping(target = "id", ignore = true)
  @Override
  Airline convertDtoToDomain(CreateAirlineCommandDto createAirlineCommandDto);
}
