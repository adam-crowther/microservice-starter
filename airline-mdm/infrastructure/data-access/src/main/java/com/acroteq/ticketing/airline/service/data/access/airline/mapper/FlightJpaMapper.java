package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.airline.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public abstract class FlightJpaMapper implements JpaMapper<Flight, FlightJpaEntity> {

  @Getter
  @Autowired
  private FlightJpaRepository repository;

  @Mapping(target = "price.amount", source = "priceAmount")
  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Override
  public abstract Flight convert(FlightJpaEntity entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  public abstract FlightJpaEntity convertNew(Flight entity);

  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  public abstract FlightJpaEntity convertExisting(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);
}