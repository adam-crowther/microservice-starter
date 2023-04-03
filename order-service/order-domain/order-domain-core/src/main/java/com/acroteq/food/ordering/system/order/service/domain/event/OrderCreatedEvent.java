package com.acroteq.food.ordering.system.order.service.domain.event;

import com.acroteq.food.ordering.system.domain.event.DomainEvent;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class OrderCreatedEvent extends OrderEvent {

}
