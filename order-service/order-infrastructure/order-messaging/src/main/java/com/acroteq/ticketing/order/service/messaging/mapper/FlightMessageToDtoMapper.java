package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.order.service.domain.dto.airline.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface FlightMessageToDtoMapper {

  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Mapping(target = "price.amount", source = "priceAmount")
  FlightDto convertMessageToDto(Flight flight);
}
