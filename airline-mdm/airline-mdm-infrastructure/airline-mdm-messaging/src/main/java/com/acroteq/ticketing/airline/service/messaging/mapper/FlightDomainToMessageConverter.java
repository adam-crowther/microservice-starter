package com.acroteq.ticketing.airline.service.messaging.mapper;

import com.acroteq.ticketing.airline.service.domain.entity.Flight;
import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.FlightIdMapper;
import com.google.common.collect.ImmutableList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Mapper(uses = { FlightIdMapper.class, CurrencyIdMapper.class, DateTimeMapper.class },
        imports = { UUID.class, Instant.class })
public interface FlightDomainToMessageConverter {

  @Mapping(target = "priceCurrencyId", source = "price.currencyId")
  @Mapping(target = "priceAmount", source = "price.amount")
  com.acroteq.ticketing.kafka.airline.avro.model.Flight convertDomainToMessage(Flight flight);

  List<com.acroteq.ticketing.kafka.airline.avro.model.Flight> convertDomainToMessage(ImmutableList<Flight> flight);
}
