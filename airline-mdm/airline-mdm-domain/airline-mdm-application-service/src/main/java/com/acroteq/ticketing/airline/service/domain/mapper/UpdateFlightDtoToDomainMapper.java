package com.acroteq.ticketing.airline.service.domain.mapper;

import com.acroteq.ticketing.airline.service.domain.dto.update.UpdateFlightCommandDto;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = { FlightIdMapper.class, DateTimeMapper.class })
public interface UpdateFlightDtoToDomainMapper {

  Flight convertDtoToDomain(UpdateFlightCommandDto dto);

  List<Flight> convertDtoToDomain(List<UpdateFlightCommandDto> dtos);
}
