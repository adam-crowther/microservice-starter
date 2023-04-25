package com.acroteq.ticketing.approval.service.domain.entity.order;

import static com.acroteq.ticketing.domain.validation.ValidationResult.fail;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PAID;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.validation.ValidationResult.ValidationResultBuilder;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
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
public class Order extends BaseEntity<OrderId> {

  @NonNull
  private final OrderStatus orderStatus;
  @NonNull
  private final Airline airline;
  @NonNull
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

    public B items(@Nullable final List<OrderItem> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
