package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval;

import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalResponseDto;

public interface AirlineApprovalResponseMessageListener {

  void orderApproved(AirlineApprovalResponseDto airlineApprovalResponse);

  void orderRejected(AirlineApprovalResponseDto airlineApprovalResponse);
}
