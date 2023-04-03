package com.acroteq.food.ordering.system.order.service.domain.event;

import com.acroteq.food.ordering.system.domain.event.DomainEvent;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class OrderEvent extends DomainEvent<Order> {

  private final Order order;
}
