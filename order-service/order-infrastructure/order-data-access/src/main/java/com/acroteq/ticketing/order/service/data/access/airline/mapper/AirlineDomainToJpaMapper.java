package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import org.mapstruct.Mapper;

@Mapper(uses = { AirlineIdMapper.class, FlightDomainToJpaMapper.class })
public interface AirlineDomainToJpaMapper {

  AirlineJpaEntity convertDomainToJpa(Airline airline);
}
