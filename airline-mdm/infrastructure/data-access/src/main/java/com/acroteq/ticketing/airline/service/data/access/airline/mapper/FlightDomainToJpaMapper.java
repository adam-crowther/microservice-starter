package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.AirlineJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.airline.service.domain.exception.AirlineNotFoundException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public abstract class FlightDomainToJpaMapper implements DomainToJpaMapper<Flight, FlightJpaEntity> {

  @Autowired
  AirlineJpaRepository airlineJpaRepository;

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "airline", expression = "java(resolveAirline(entity.getAirlineCode()))")
  @Override
  public abstract FlightJpaEntity convertDomainToJpa(Flight entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "airline", ignore = true)
  @Override
  public abstract FlightJpaEntity convertDomainToJpa(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);

  protected AirlineJpaEntity resolveAirline(final String airlineCode) {
    return airlineJpaRepository.findByCode(airlineCode)
                               .orElseThrow(() -> new AirlineNotFoundException(airlineCode));
  }
}