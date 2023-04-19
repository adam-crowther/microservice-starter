package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.airline.service.domain.event.OrderApprovalEvent;
import com.acroteq.ticketing.airline.service.domain.ports.input.message.listener.AirlineApprovalRequestMessageListener;
import com.acroteq.ticketing.airline.service.domain.visitor.OrderApprovalEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AirlineApprovalRequestMessageListenerImpl implements AirlineApprovalRequestMessageListener {

  private final AirlineApprovalRequestHelper airlineApprovalRequestHelper;
  private final OrderApprovalEventPublisherVisitor eventPublisher;

  @Transactional
  @Override
  public void approveOrder(final AirlineApprovalRequestDto airlineApprovalRequest) {
    final OrderApprovalEvent orderApprovalEvent = airlineApprovalRequestHelper.checkOrder(airlineApprovalRequest);
    orderApprovalEvent.accept(eventPublisher);
  }
}
