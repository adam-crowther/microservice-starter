package com.acroteq.ticketing.airline.service.data.access.airline.mapper;

import com.acroteq.ticketing.airline.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.id.FlightIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public interface FlightJpaToDomainMapper extends JpaToDomainMapper<FlightJpaEntity, Flight> {

  @Mapping(target = "price.amount", source = "priceAmount")
  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Override
  Flight convertJpaToDomain(FlightJpaEntity entity);
}
