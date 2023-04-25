package com.acroteq.ticketing.approval.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.approval.service.domain.dto.FlightDto;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CurrencyIdMapper.class)
public interface FlightMessageToDtoMapper {

  @Mapping(target = "price.currencyId", source = "priceCurrencyId")
  @Mapping(target = "price.amount", source = "priceAmount")
  FlightDto convertMessageToDto(Flight flight);
}
