package com.acroteq.ticketing.airline.service.domain.entity;

import com.acroteq.ticketing.airline.service.domain.valueobject.OrderApprovalId;
import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderApproval extends AggregateRoot<OrderApprovalId> {

  private final Airline airline;
  private final Order order;
  private final OrderApprovalStatus approvalStatus;

  public OrderId getOrderId() {
    return order.getId();
  }
}
