package com.acroteq.ticketing.approval.service.data.access.airline.adapter;

import com.acroteq.infrastructure.data.access.repository.WriteRepositoryImpl;
import com.acroteq.ticketing.approval.service.data.access.airline.entity.OrderApprovalJpaEntity;
import com.acroteq.ticketing.approval.service.data.access.airline.mapper.OrderApprovalJpaMapper;
import com.acroteq.ticketing.approval.service.data.access.airline.repository.OrderApprovalJpaRepository;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.ports.output.repository.OrderApprovalRepository;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalId;
import org.springframework.stereotype.Component;

@Component
public class OrderApprovalRepositoryImpl
    extends WriteRepositoryImpl<OrderApprovalId, OrderApproval, OrderApprovalJpaEntity>
    implements OrderApprovalRepository {

  public OrderApprovalRepositoryImpl(
      final OrderApprovalJpaRepository jpaRepository, final OrderApprovalJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);
  }
}
