package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineCreatedDto;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineDeletedDto;
import com.acroteq.ticketing.order.service.domain.dto.airline.AirlineUpdatedDto;

public interface AirlineEventMessageListener {

  void airlineCreated(AirlineCreatedDto airlineCreatedDto);

  void airlineUpdated(AirlineUpdatedDto airlineUpdatedDto);

  void airlineDeleted(AirlineDeletedDto airlineDeletedDto);
}
