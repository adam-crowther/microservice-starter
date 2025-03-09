package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public interface FlightDomainToJpaMapper extends DomainToJpaMapper<Flight, FlightJpaEntity> {

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);
}