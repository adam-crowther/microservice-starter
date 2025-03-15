package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateAirlineCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, UpdateFlightDtoToDomainMapper.class, DateTimeMapper.class })
public interface UpdateAirlineDtoToDomainMapper extends DtoToDomainMapper<UpdateAirlineCommandDto, Airline> {

  @Mapping(target = "audit", ignore = true)
  @Override
  Airline convertDtoToDomain(UpdateAirlineCommandDto dto);
}
