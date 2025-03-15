package com.acroteq.ticketing.approval.service.domain.entity.order;

import com.acroteq.domain.entity.AggregateRoot;
import com.acroteq.domain.valueobject.OrderApprovalStatus;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderApproval extends AggregateRoot<OrderApprovalId> {

  @NonNull
  private final Airline airline;
  @NonNull
  private final OrderId orderId;
  @NonNull
  private final Long orderVersion;
  @NonNull
  private final OrderApprovalStatus approvalStatus;
}