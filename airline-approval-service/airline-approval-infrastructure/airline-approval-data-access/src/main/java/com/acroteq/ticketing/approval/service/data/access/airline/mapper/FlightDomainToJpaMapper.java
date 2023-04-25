package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public abstract class FlightDomainToJpaMapper {

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  abstract FlightJpaEntity convertDomainToJpa(Flight entity);
}