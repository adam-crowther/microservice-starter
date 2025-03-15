package com.acroteq.ticketing.approval.service.data.access.airline.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.FlightJpaEntity;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CurrencyIdMapper.class, FlightIdMapper.class })
public interface FlightJpaToDomainMapper extends JpaToDomainMapper<FlightJpaEntity, Flight> {

  @Mapping(target = "price.amount", source = "priceAmount")
  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Override
  Flight convertJpaToDomain(FlightJpaEntity entity);
}
