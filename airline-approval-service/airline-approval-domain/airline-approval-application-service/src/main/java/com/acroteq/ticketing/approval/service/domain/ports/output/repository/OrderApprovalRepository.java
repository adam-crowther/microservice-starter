package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;

public interface OrderApprovalRepository {

  OrderApproval save(OrderApproval orderApproval);
}
