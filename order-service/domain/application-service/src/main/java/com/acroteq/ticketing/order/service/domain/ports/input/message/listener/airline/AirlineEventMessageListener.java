package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineEventDto;

public interface AirlineEventMessageListener {

  void airlineCreatedOrUpdated(AirlineEventDto airlineEventDto);

  void airlineDeleted(Long id);
}
