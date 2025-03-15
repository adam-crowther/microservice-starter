package com.acroteq.ticketing.approval.service.domain.entity.order;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.OrderItemId;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
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

  CashValue getAmount() {
    return flight.getPrice()
                 .multiply(BigDecimal.valueOf(quantity));
  }
}
