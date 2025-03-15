package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.create.CreateFlightCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class, DateTimeMapper.class })
public interface CreateFlightDtoToDomainMapper extends DtoToDomainMapper<CreateFlightCommandDto, Flight> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Override
  Flight convertDtoToDomain(CreateFlightCommandDto createFlightCommandDto);
}
