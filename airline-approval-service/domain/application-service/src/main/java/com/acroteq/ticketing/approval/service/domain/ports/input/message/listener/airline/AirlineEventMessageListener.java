package com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;

public interface AirlineEventMessageListener {

  void airlineCreatedOrUpdated(Airline airline);

  void airlineDeleted(String airlineCode);
}
