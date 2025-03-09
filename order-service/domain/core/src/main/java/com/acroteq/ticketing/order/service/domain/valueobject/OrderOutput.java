package com.acroteq.ticketing.order.service.domain.valueobject;

import com.acroteq.ticketing.order.service.domain.entity.Order;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class OrderOutput {

  UUID sagaId;
  Order order;
}
