package com.acroteq.food.ordering.system.order.service.domain.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@ToString
public class OrderPaidEvent extends OrderEvent {

}
