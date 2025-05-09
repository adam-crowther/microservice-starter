package com.acroteq.ticketing.order.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.EventIdJpaMapper;
import com.acroteq.infrastructure.mapper.JpaMapper;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.repository.FlightJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class, CurrencyIdMapper.class, EventIdJpaMapper.class })
public abstract class FlightJpaMapper implements JpaMapper<Flight, FlightJpaEntity> {

  @Getter
  @Autowired
  private FlightJpaRepository repository;

  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Mapping(target = "price.amount", source = "priceAmount")
  @Override
  public abstract Flight convert(FlightJpaEntity flightJpaEntity);

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  public abstract FlightJpaEntity convertNew(Flight entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Mapping(target = "priceAmount", source = "entity.price.amount")
  @Mapping(target = "priceCurrencyId", source = "entity.price.currencyId")
  @Override
  public abstract FlightJpaEntity convertExisting(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);
}
