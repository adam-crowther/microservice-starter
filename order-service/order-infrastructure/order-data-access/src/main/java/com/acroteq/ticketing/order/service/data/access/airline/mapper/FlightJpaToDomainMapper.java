package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, FlightIdMapper.class, AirlineIdMapper.class })
public interface FlightJpaToDomainMapper extends JpaToDomainMapper<FlightJpaEntity, Flight> {

  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Mapping(target = "price.amount", source = "priceAmount")
  @Override
  Flight convertJpaToDomain(FlightJpaEntity flightJpaEntity);
}
