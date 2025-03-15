package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.OrderApprovalStatus;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalOutput;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AirlineDomainServiceImpl implements AirlineDomainService {

  @Override
  public OrderApprovalOutput validateOrder(final Order order) {
    final OrderId orderId = order.getId();
    final Long orderVersion = order.getVersion();
    log.info("Validating order with orderId: {}", orderId);

    final ValidationResult result = order.validate();
    final Airline airline = order.getAirline();

    final OrderApprovalStatus approvalStatus = result.getApprovalStatus();
    log.info("Order is {} for order id: {}", approvalStatus, orderId);
    final OrderApproval orderApproval = OrderApproval.builder()
                                                     .orderId(orderId)
                                                     .orderVersion(orderVersion)
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

