package com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineDto;

public interface AirlineEventMessageListener {

  void airlineCreated(AirlineDto airlineCreatedDto);

  void airlineUpdated(AirlineDto airlineUpdatedDto);

  void airlineDeleted(Long airlineId);
}
