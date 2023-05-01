package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.FlightDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;

@Mapper(uses = { FlightIdMapper.class })
public interface FlightDtoToDomainMapper extends DtoToDomainMapper<FlightDto, Flight> {

  @Override
  Flight convertDtoToDomain(FlightDto flight);
}
