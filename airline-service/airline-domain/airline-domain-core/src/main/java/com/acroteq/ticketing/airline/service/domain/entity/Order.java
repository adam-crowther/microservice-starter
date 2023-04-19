package com.acroteq.ticketing.airline.service.domain.entity;

import static com.acroteq.ticketing.domain.validation.ValidationResult.fail;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PAID;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.validation.ValidationResult.ValidationResultBuilder;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Order extends BaseEntity<OrderId> {

  private final OrderStatus orderStatus;
  private final Airline airline;
  private final ImmutableList<OrderItem> items;

  public ValidationResult validate() {
    final ValidationResultBuilder result = ValidationResult.builder();
    if (orderStatus != PAID) {
      result.addFailure("Payment is not completed for order: %s", id);
    }

    items.stream()
         .map(OrderItem::getFlight)
         .filter(flight -> !flight.isAvailable())
         .map(flight -> fail("Flight with id: %s is not available", flight.getId()))
         .forEach(result::addValidationResult);

    return result.build();
  }

  public CashValue getTotalAmount() {
    // TODO
    return items.stream()
                .map(OrderItem::getAmount)
                .reduce(CashValue.ZERO, CashValue::add);
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class OrderBuilder<C extends Order, B extends OrderBuilder<C, B>>
      extends BaseEntity.BaseEntityBuilder<OrderId, C, B> {

    public B items(final List<OrderItem> items) {
      this.items = ImmutableList.copyOf(items);
      return this.self();
    }
  }
}
