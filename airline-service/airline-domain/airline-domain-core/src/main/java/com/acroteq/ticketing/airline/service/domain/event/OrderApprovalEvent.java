package com.acroteq.ticketing.airline.service.domain.event;

import com.acroteq.ticketing.airline.service.domain.entity.OrderApproval;
import com.acroteq.ticketing.airline.service.domain.event.visitor.OrderApprovalEventVisitor;
import com.acroteq.ticketing.domain.event.DomainEvent;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class OrderApprovalEvent extends DomainEvent<OrderApproval> {

  private final OrderApproval orderApproval;
  private final ValidationResult result;

  public abstract void accept(OrderApprovalEventVisitor visitor);

  public OrderId getOrderId() {
    return orderApproval.getOrderId();
  }
}
