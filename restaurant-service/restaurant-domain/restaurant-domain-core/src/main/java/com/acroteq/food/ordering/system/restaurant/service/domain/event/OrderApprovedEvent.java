package com.acroteq.food.ordering.system.restaurant.service.domain.event;

import com.acroteq.food.ordering.system.restaurant.service.domain.event.visitor.OrderApprovalEventVisitor;
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
