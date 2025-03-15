package com.acroteq.ticketing.approval.service.domain.ports.output.repository;

import com.acroteq.application.repository.WriteRepository;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalId;

public interface OrderApprovalRepository extends WriteRepository<OrderApprovalId, OrderApproval> {}
