package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.infrastructure.mapper.EventIdDomainToJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapstructConfig.class,
        uses = { CurrencyIdMapper.class, FlightIdMapper.class, EventIdDomainToJpaMapper.class })
public interface FlightDomainToJpaMapper extends DomainToJpaMapper<Flight, FlightJpaEntity> {

  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight entity);

  @Mapping(target = "version", source = "entity.version")
  @Mapping(target = "eventId", source = "entity.eventId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Override
  FlightJpaEntity convertDomainToJpa(Flight entity, @MappingTarget FlightJpaEntity jpaEntity);
}