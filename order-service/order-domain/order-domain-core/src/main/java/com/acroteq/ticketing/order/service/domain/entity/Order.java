package com.acroteq.ticketing.order.service.domain.entity;

import static com.acroteq.ticketing.domain.validation.ValidationResult.combine;
import static com.acroteq.ticketing.domain.validation.ValidationResult.pass;
import static com.acroteq.ticketing.domain.valueobject.CashValue.ZERO;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.APPROVED;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.CANCELLING;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PAID;
import static com.acroteq.ticketing.domain.valueobject.OrderStatus.PENDING;
import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;

import com.acroteq.ticketing.domain.entity.AggregateRoot;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.order.service.domain.exception.InvalidOrderPrestateException;
import com.acroteq.ticketing.order.service.domain.valueobject.StreetAddress;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import com.google.common.collect.ImmutableList;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class Order extends AggregateRoot<OrderId> {

  @NonNull
  private final Customer customer;
  @NonNull
  private final Airline airline;
  @NonNull
  private final StreetAddress streetAddress;
  @NonNull
  private final ImmutableList<OrderItem> items;

  @NonNull
  @Builder.Default
  private final TrackingId trackingId = TrackingId.random();
  @NonNull
  @Builder.Default
  private final OrderStatus orderStatus = PENDING;
  @NonNull
  @Builder.Default
  private final ValidationResult result = pass();

  public CashValue getPrice() {
    return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(ZERO, CashValue::add);
  }

  public void validateOrder() {
    items.forEach(OrderItem::validateOrderItem);
  }

  public Order pay() {
    checkOrderState("pay", PENDING);
    return toBuilder().orderStatus(PAID)
                      .build();
  }

  public Order approve() {
    checkOrderState("approve", PAID);
    return toBuilder().orderStatus(APPROVED)
                      .build();
  }

  public Order initCancel(final ValidationResult cancelResult) {
    checkOrderState("initCancel", PAID);
    final ValidationResult combinedResult = combine(result, cancelResult);
    return toBuilder().orderStatus(CANCELLING)
                      .result(combinedResult)
                      .build();
  }

  public Order cancel(final ValidationResult cancelResult) {
    checkOrderState("cancel", PENDING, CANCELLING);
    final ValidationResult combinedResult = combine(result, cancelResult);
    return toBuilder().orderStatus(CANCELLING)
                      .result(combinedResult)
                      .build();
  }

  private void checkOrderState(final String action, final OrderStatus... statuses) {
    final Set<OrderStatus> required = Set.of(statuses);
    checkPrecondition(required.contains(orderStatus), InvalidOrderPrestateException::new, required, action);
  }

  @SuppressWarnings("PublicInnerClass")
  public abstract static class OrderBuilder<C extends Order, B extends OrderBuilder<C, B>>
      extends AggregateRoot.AggregateRootBuilder<OrderId, C, B> {

    public B items(@Nullable final List<OrderItem> items) {
      this.items = Optional.ofNullable(items)
                           .map(ImmutableList::copyOf)
                           .orElse(ImmutableList.of());
      return this.self();
    }
  }
}
