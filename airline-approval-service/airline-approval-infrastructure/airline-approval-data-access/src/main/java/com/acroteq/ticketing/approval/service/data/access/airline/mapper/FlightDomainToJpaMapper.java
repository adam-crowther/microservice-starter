package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public interface FlightDomainToJpaMapper extends DomainToJpaMapper<Flight, FlightJpaEntity> {

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight entity);
}