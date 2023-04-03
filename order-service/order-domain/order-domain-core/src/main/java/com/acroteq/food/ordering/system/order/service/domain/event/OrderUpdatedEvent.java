package com.acroteq.food.ordering.system.order.service.domain.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class OrderUpdatedEvent extends OrderEvent {

}
