package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateFlightCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class, DateTimeMapper.class })
public interface UpdateFlightDtoToDomainMapper extends DtoToDomainMapper<UpdateFlightCommandDto, Flight> {

  @Override
  Flight convertDtoToDomain(UpdateFlightCommandDto dto);

  List<Flight> convertDtoToDomain(List<UpdateFlightCommandDto> dtos);
}
