package com.acroteq.ticketing.order.service.messaging.mapper.airline;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.MessageToDomainMapper;
import com.acroteq.application.mapper.id.CurrencyIdMapper;
import com.acroteq.application.mapper.id.FlightIdMapper;
import com.acroteq.domain.valueobject.EventId;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.function.Function;

@Mapper(config = MapstructConfig.class, uses = { FlightIdMapper.class, CurrencyIdMapper.class })
public abstract class FlightMapper
    implements MessageToDomainMapper<Flight, com.acroteq.ticketing.order.service.domain.entity.Flight> {

  @Mapping(target = "price.currencyId", source = "flight.priceCurrencyId")
  @Mapping(target = "price.amount", source = "flight.priceAmount")
  @Override
  public abstract com.acroteq.ticketing.order.service.domain.entity.Flight convert(Flight flight, EventId eventId);

  public List<com.acroteq.ticketing.order.service.domain.entity.Flight> convert(
      final List<Flight> flights, final EventId eventId) {
    return flights.stream()
                  .map(convert(eventId))
                  .toList();
  }

  private Function<Flight, com.acroteq.ticketing.order.service.domain.entity.Flight> convert(final EventId eventId) {
    return flight -> convert(flight, eventId);
  }
}
