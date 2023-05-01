package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, CurrencyIdMapper.class })
public interface FlightDomainToJpaMapper extends DomainToJpaMapper<Flight, FlightJpaEntity> {

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight flight);
}
