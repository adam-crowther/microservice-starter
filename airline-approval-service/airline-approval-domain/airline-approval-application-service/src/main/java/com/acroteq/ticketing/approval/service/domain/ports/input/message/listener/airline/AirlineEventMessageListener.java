package com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineEventDto;

public interface AirlineEventMessageListener {

  void airlineCreatedOrUpdated(AirlineEventDto airlineEventDto);

  void airlineDeleted(Long airlineId);
}
