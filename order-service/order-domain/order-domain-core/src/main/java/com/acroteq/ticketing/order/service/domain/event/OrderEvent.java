package com.acroteq.ticketing.order.service.domain.event;

import com.acroteq.ticketing.domain.event.SagaEvent;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class OrderEvent extends SagaEvent<Order> {

  @NonNull
  private final Order order;
}
