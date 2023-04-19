package com.acroteq.ticketing.order.service.domain.event;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class OrderCancelledEvent extends OrderEvent {

}
