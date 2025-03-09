package com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.order;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;

public interface OrderApprovalRequestMessageListener {

  void checkOrder(AirlineApprovalRequestDto airlineApprovalRequest);
}
