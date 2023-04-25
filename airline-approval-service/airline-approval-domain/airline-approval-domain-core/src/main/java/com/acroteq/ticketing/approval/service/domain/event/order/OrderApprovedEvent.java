package com.acroteq.ticketing.approval.service.domain.event.order;

import com.acroteq.ticketing.approval.service.domain.event.order.visitor.OrderApprovalEventVisitor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class OrderApprovedEvent extends OrderApprovalEvent {

  @Override
  public void accept(final OrderApprovalEventVisitor visitor) {
    visitor.visit(this);
  }
}
