package com.acroteq.ticketing.approval.service.domain.event.order;

import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.APPROVED;
import static com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus.REJECTED;
import static com.google.common.base.Preconditions.checkNotNull;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.approval.service.domain.entity.order.OrderApproval;
import com.acroteq.ticketing.approval.service.domain.exception.UnexpectedOrderApprovalStatusException;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@NoArgsConstructor(access = PACKAGE)
public class PolymorphicOrderApprovalEventBuilder {

  private UUID sagaId;
  private OrderApproval orderApproval;
  private ValidationResult result;

  private final Map<OrderApprovalStatus, Supplier<OrderApprovalEvent>> builders = Map.of(APPROVED,
                                                                                         this::buildOrderApprovedEvent,
                                                                                         REJECTED,
                                                                                         this::buildOrderRejectedEvent);

  public PolymorphicOrderApprovalEventBuilder sagaId(final UUID sagaId) {
    this.sagaId = sagaId;
    return this;
  }

  public PolymorphicOrderApprovalEventBuilder orderApproval(final OrderApproval orderApproval) {
    this.orderApproval = orderApproval;
    return this;
  }

  public PolymorphicOrderApprovalEventBuilder result(final ValidationResult result) {
    this.result = result;
    return this;
  }

  public OrderApprovalEvent build() {
    checkNotNull(sagaId, "sagaId must be set.");
    checkNotNull(orderApproval, "savedOrderApproval must be set.");
    checkNotNull(result, "result must be set.");

    final OrderApprovalStatus orderApprovalStatus = orderApproval.getApprovalStatus();
    return Optional.of(orderApprovalStatus)
                   .map(builders::get)
                   .map(Supplier::get)
                   .orElseThrow(() -> new UnexpectedOrderApprovalStatusException(orderApprovalStatus));
  }

  private OrderApprovalEvent buildOrderApprovedEvent() {
    return OrderApprovedEvent.builder()
                             .sagaId(sagaId)
                             .orderApproval(orderApproval)
                             .result(result)
                             .build();
  }

  private OrderApprovalEvent buildOrderRejectedEvent() {
    return OrderRejectedEvent.builder()
                             .sagaId(sagaId)
                             .orderApproval(orderApproval)
                             .result(result)
                             .build();
  }
}
