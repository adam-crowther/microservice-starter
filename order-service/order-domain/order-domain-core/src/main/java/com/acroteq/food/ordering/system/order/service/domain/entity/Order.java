package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderStatus;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.exception.InvalidOrderPrestateException;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.acroteq.food.ordering.system.domain.validation.ValidationResult.pass;
import static com.acroteq.food.ordering.system.domain.valueobject.CashValue.ZERO;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.APPROVED;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.CANCELLED;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.CANCELLING;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.PAID;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.PENDING;
import static com.acroteq.food.ordering.system.precondition.Precondition.checkPrecondition;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public class Order extends AggregateRoot<OrderId> {

  @NonNull
  private final CustomerId customerId;
  @NonNull
  private final RestaurantId restaurantId;
  @NonNull
  private final StreetAddress streetAddress;
  @NonNull
  @Builder.Default
  private final List<OrderItem> items = new ArrayList<>();

  @NonNull
  @Builder.Default
  private TrackingId trackingId = TrackingId.random();
  @NonNull
  @Builder.Default
  private OrderStatus orderStatus = PENDING;
  @NonNull
  @Builder.Default
  private final ValidationResult result = pass();

  public void initialiseOrder() {
    setId(OrderId.random());
    initialiseOrderItems();
  }

  private void initialiseOrderItems() {
    long itemId = 1L;
    for (final OrderItem item : items) {
      item.initialiseOrderItem(getId(), OrderItemId.of(itemId));
      itemId++;
    }
  }

  public CashValue getPrice() {
    return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(ZERO, CashValue::add);
  }

  public void validateOrder() {
    items.forEach(OrderItem::validateOrderItem);
  }

  public void pay() {
    checkOrderState("pay", PENDING);
    orderStatus = PAID;
  }

  public void approve() {
    checkOrderState("approve", PAID);
    orderStatus = APPROVED;
  }

  public void initCancel(final ValidationResult cancelResult) {
    checkOrderState("initCancel", PAID);
    orderStatus = CANCELLING;
    result.add(cancelResult);
  }

  public void cancel(final ValidationResult cancelResult) {
    checkOrderState("initCancel", PENDING, CANCELLING);
    orderStatus = CANCELLED;
    result.add(cancelResult);
  }

  private void checkOrderState(final String action, final OrderStatus... statuses) {
    final Set<OrderStatus> required = Set.of(statuses);
    checkPrecondition(
        required.contains(orderStatus), InvalidOrderPrestateException::new, required, action);
  }
}
