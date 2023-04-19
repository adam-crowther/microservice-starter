package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;

public interface OrderApprovalRepository {

  OrderApproval save(OrderApproval orderApproval);
}
