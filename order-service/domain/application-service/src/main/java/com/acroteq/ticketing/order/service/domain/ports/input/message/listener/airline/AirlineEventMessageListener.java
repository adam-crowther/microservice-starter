package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.order.service.domain.entity.Airline;

public interface AirlineEventMessageListener {

  void airlineCreatedOrUpdated(Airline airline);

  void airlineDeleted(String code);
}
