package com.acroteq.ticketing.approval.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.FlightDto;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;

@Mapper(uses = { FlightIdMapper.class })
public interface FlightDtoToDomainMapper {

  Flight convertDomainToDto(FlightDto flight);
}
