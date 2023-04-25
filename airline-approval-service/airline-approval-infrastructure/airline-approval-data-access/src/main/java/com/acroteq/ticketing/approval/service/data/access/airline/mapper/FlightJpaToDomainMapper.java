package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public interface FlightJpaToDomainMapper {

  @Mapping(target = "price.amount", source = "priceAmount")
  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  Flight convertJpaToDomain(FlightJpaEntity entity);
}
