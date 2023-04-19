package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, FlightIdMapper.class, AirlineIdMapper.class })
public interface AirlineJpaToFlightDomainMapper {

  @Mapping(target = "id", source = "flightId")
  @Mapping(target = "price.currencyId", source = "flightPriceCurrencyId")
  @Mapping(target = "price.amount", source = "flightPriceAmount")
  Flight convertEntityToDomain(AirlineJpaEntity airlineJpaEntity);
}
