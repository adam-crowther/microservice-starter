package com.acroteq.ticketing.airline.service.domain.entity;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderItem extends BaseEntity<OrderItemId> {

  private final Flight flight;

  private final int quantity;

  CashValue getAmount() {
    return flight.getPrice()
                 .multiply(quantity);
  }
}
