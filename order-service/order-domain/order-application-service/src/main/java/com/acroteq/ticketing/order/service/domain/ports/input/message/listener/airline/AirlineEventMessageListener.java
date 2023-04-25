package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDto;

public interface AirlineEventMessageListener {

  void airlineCreated(AirlineDto airlineCreatedDto);

  void airlineUpdated(AirlineDto airlineUpdatedDto);

  void airlineDeleted(Long airlineId);
}
