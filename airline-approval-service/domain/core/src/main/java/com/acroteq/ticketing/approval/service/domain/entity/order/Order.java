package com.acroteq.ticketing.approval.service.domain.entity.order;

import static com.acroteq.domain.validation.ValidationResult.fail;
import static com.acroteq.domain.valueobject.OrderStatus.PAID;

import com.acroteq.domain.entity.PrimaryEntity;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.validation.ValidationResultBuilder;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Order extends PrimaryEntity<OrderId> {

  @NonNull
  private final OrderStatus orderStatus;
  @NonNull
  private final Airline airline;
  @NonNull
  private final List<OrderItem> items;

  public ValidationResult validate() {
    final ValidationResultBuilder result = ValidationResult.builder();
    if (orderStatus != PAID) {
      result.failure("Payment is not completed for order: %s", getId());
    }

    items.stream()
         .map(OrderItem::getFlight)
         .filter(flight -> !flight.isAvailable())
         .map(flight -> fail("Flight with id: %s is not available", flight.getId()))
         .forEach(result::validationResult);

    return result.build();
  }

  public CashValue getTotalAmount() {
    return items.stream()
                .map(OrderItem::getAmount)
                .reduce(CashValue.ZERO, CashValue::add);
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class OrderBuilder<C extends Order, B extends OrderBuilder<C, B>>
      extends PrimaryEntityBuilder<OrderId, C, B> {

    public B items(@Nullable final List<OrderItem> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
