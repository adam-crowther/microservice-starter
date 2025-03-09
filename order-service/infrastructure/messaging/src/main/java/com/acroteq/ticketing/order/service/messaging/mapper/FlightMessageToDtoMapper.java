package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CurrencyIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.EventIdMessageToDtoMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.order.service.domain.dto.airline.FlightDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Function;

@Mapper(config = MapstructConfig.class, uses = CurrencyIdMapper.class)
public abstract class FlightMessageToDtoMapper implements MessageToDtoMapper<Flight, FlightDto> {

  @Autowired
  EventIdMessageToDtoMapper eventIdMapper;

  @Mapping(target = "eventId", expression = "java(eventIdMapper.convertMessageToDto(partition, offset))")
  @Mapping(target = "price.currencyId", source = "flight.priceCurrencyId")
  @Mapping(target = "price.amount", source = "flight.priceAmount")
  @Override
  public abstract FlightDto convertMessageToDto(Flight flight, Integer partition, Long offset);

  public List<FlightDto> convertMessageToDto(final List<Flight> flights, final Integer partition, final Long offset) {
    return flights.stream()
                  .map(convertMessageToDto(partition, offset))
                  .toList();
  }

  private Function<Flight, FlightDto> convertMessageToDto(final Integer partition, final Long offset) {
    return flight -> convertMessageToDto(flight, partition, offset);
  }
}
