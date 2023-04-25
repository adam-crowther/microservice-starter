package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalOutput;
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
                                                     .orderId(order.getId())
                                                     .airline(airline)
                                                     .approvalStatus(approvalStatus)
                                                     .build();
    return OrderApprovalOutput.builder()
                              .validationResult(result)
                              .orderApproval(orderApproval)
                              .build();
  }

  @Override
  public void validateAirline(final Airline airline) {
    airline.validate();
    log.info("Airline with id {} is validated", airline.getId());
  }
}

