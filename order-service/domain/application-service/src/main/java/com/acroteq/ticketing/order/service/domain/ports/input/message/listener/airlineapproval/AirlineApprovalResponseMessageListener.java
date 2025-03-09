package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval;

import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalApprovedResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalRejectedResponseDto;

public interface AirlineApprovalResponseMessageListener {

  void orderApproved(AirlineApprovalApprovedResponseDto dto);

  void orderRejected(AirlineApprovalRejectedResponseDto dto);
}
