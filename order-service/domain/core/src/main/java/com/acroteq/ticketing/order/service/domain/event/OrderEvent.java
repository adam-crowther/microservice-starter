package com.acroteq.ticketing.order.service.domain.event;

import com.acroteq.domain.event.SagaEvent;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public abstract class OrderEvent extends SagaEvent {

  @NonNull
  private final Order order;
}
