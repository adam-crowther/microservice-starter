package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovalEvent;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.order.OrderApprovalRequestMessageListener;
import com.acroteq.ticketing.approval.service.domain.visitor.OrderApprovalEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderApprovalRequestMessageListenerImpl implements OrderApprovalRequestMessageListener {

  private final AirlineApprovalRequestHelper airlineApprovalRequestHelper;
  private final OrderApprovalEventPublisherVisitor eventPublisher;

  @Transactional
  @Override
  public void checkOrder(final AirlineApprovalRequestDto airlineApprovalRequest) {
    final OrderApprovalEvent orderApprovalEvent = airlineApprovalRequestHelper.checkOrder(airlineApprovalRequest);
    orderApprovalEvent.accept(eventPublisher);
  }
}
