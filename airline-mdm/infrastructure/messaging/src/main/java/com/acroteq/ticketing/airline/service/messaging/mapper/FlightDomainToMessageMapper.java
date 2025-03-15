package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.infrastructure.mapper.DomainToMessageMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, CurrencyIdMapper.class, FlightIdMapper.class, DateTimeMapper.class })
public interface FlightDomainToMessageMapper
    extends DomainToMessageMapper<com.acroteq.ticketing.airline.service.domain.entity.Flight, Flight> {

  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  @Override
  Flight convertDomainToMessage(com.acroteq.ticketing.airline.service.domain.entity.Flight flight);
}
