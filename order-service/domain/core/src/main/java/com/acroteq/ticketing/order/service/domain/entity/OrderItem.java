package com.acroteq.ticketing.order.service.domain.entity;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderItem extends PrimaryEntity<OrderItemId> {

  @NonNull
  private final Flight flight;
  private final int quantity;

  public CashValue getPrice() {
    return flight.getPrice();
  }

  CashValue getSubTotal() {
    return flight.getPrice()
                 .multiply(BigDecimal.valueOf(quantity));
  }

  void validateOrderItem() {
    flight.validatePrice();
  }
}
