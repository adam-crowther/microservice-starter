package com.acroteq.ticketing.order.service.domain.event;

import com.acroteq.ticketing.domain.event.DomainEvent;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class OrderEvent extends DomainEvent<Order> {

  private final Order order;
}
