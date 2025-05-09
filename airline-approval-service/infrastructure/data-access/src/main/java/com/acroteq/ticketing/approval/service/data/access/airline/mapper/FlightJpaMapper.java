package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.EventIdJpaMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class, EventIdJpaMapper.class })
public abstract class FlightJpaMapper implements JpaMapper<Flight, FlightJpaEntity> {

  @Getter
  @Autowired
  private FlightJpaRepository repository;

  @Mapping(target = "price.amount", source = "priceAmount")
  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Override
  public abstract Flight convert(FlightJpaEntity entity);

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  public abstract FlightJpaEntity convertNew(Flight entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  public abstract FlightJpaEntity convertExisting(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);
}