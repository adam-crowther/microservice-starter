package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, UpdateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface UpdateAirlineDtoToDomainMapper extends DtoToDomainMapper<UpdateAirlineCommandDto, Airline> {

  @Mapping(target = "audit", ignore = true)
  @Override
  Airline convertDtoToDomain(UpdateAirlineCommandDto dto);
}
