package com.acroteq.ticketing.airline.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.airline.service.domain.dto.AirlineApprovalRequestDto;

public interface AirlineApprovalRequestMessageListener {

  void approveOrder(AirlineApprovalRequestDto airlineApprovalRequest);
}
