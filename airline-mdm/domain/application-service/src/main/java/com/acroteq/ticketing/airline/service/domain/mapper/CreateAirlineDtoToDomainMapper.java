package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, CreateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface CreateAirlineDtoToDomainMapper extends DtoToDomainMapper<CreateAirlineCommandDto, Airline> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Override
  Airline convertDtoToDomain(CreateAirlineCommandDto createAirlineCommandDto);
}
