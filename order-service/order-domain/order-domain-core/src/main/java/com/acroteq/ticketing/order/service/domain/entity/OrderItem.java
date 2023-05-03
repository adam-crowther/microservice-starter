package com.acroteq.ticketing.order.service.domain.entity;

import com.acroteq.ticketing.domain.entity.MasterEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderItem extends MasterEntity<OrderItemId> {

  private final OrderId orderId;
  @NonNull
  private final Flight flight;
  private final int quantity;

  public CashValue getPrice() {
    return flight.getPrice();
  }

  CashValue getSubTotal() {
    return flight.getPrice()
                 .multiply(quantity);
  }

  void validateOrderItem() {
    flight.validatePrice();
  }
}
