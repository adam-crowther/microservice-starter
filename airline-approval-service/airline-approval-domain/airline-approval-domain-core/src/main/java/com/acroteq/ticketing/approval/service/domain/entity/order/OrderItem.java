package com.acroteq.ticketing.approval.service.domain.entity.order;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Flight;
import com.acroteq.ticketing.domain.entity.PrimaryEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderItemId;
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
