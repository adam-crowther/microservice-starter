package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Airline;
import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.airline.service.domain.valueobject.OrderApprovalOutput;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirlineDomainServiceImpl implements AirlineDomainService {

  @Override
  public OrderApprovalOutput validateOrder(final Order order) {
    log.info("Validating order with orderId: {}", order.getId());

    final ValidationResult result = order.validate();
    final Airline airline = order.getAirline();

    final OrderApprovalStatus approvalStatus = result.getApprovalStatus();
    log.info("Order is {} for order id: {}", approvalStatus, order.getId());
    final OrderApproval orderApproval = OrderApproval.builder()
                                                     .order(order)
                                                     .airline(airline)
                                                     .approvalStatus(approvalStatus)
                                                     .build();
    return new OrderApprovalOutput(result, orderApproval);
  }
}

