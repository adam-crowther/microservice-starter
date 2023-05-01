package com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.airline;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineCreatedDto;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineDeletedDto;
import com.acroteq.ticketing.approval.service.domain.dto.AirlineUpdatedDto;

public interface AirlineEventMessageListener {

  void airlineCreated(AirlineCreatedDto airlineCreatedDto);

  void airlineUpdated(AirlineUpdatedDto airlineUpdatedDto);

  void airlineDeleted(AirlineDeletedDto airlineId);
}
