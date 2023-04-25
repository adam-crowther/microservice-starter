package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { FlightIdMapper.class, CurrencyIdMapper.class })
public interface FlightDomainToJpaMapper {

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  FlightJpaEntity convertDomainToJpa(Flight flight);
}
