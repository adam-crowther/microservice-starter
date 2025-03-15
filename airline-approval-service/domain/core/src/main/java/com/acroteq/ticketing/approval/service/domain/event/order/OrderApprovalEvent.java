package com.acroteq.ticketing.approval.service.domain.event.order;

import com.acroteq.domain.event.SagaEvent;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.event.order.visitor.OrderApprovalEventVisitor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class OrderApprovalEvent extends SagaEvent {

  @NonNull
  private final OrderApproval orderApproval;
  @NonNull
  private final ValidationResult result;

  public static PolymorphicOrderApprovalEventBuilder polymorphicBuilder() {
    return new PolymorphicOrderApprovalEventBuilder();
  }

  public abstract void accept(OrderApprovalEventVisitor visitor);

  public OrderId getOrderId() {
    return orderApproval.getOrderId();
  }
}
