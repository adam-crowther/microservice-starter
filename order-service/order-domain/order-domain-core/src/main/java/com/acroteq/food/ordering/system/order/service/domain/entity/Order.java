package com.acroteq.food.ordering.system.order.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.AggregateRoot;
import com.acroteq.food.ordering.system.domain.valueobject.*;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.OrderItemId;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.StreetAddress;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.acroteq.food.ordering.system.domain.valueobject.Money.ZERO;
import static com.acroteq.food.ordering.system.domain.valueobject.OrderStatus.*;
import static com.acroteq.food.ordering.system.order.service.domain.precondition.OrderDomainPrecondition.checkPrecondition;

@SuperBuilder
@Getter
@ToString
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
  private final List<String> failureMessages = new ArrayList<>();

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

  public Money getPrice() {
    return items.stream()
                .map(OrderItem::getSubTotal)
                .reduce(ZERO, Money::add);
  }

  public void validateOrder() {
    validateInitialOrder();
    items.forEach(OrderItem::validateOrderItem);
  }

  private void validateInitialOrder() {
    checkPrecondition(getId() != null, "Order id can not be null");
  }

  public void pay() {
    checkOrderState("pay", PENDING);
    orderStatus = PAID;
  }

  public void approve() {
    checkOrderState("approve", PAID);
    orderStatus = APPROVED;
  }

  public void initCancel(final List<String> failureMessages) {
    checkOrderState("initCancel", PAID);
    orderStatus = CANCELLING;
    storeFailureMessages(failureMessages);
  }

  public void cancel(final List<String> failureMessages) {
    checkOrderState("initCancel", PENDING, CANCELLING);
    orderStatus = CANCELLED;
    storeFailureMessages(failureMessages);
  }

  private void checkOrderState(final String action, final OrderStatus... statuses) {
    final Set<OrderStatus> required = Set.of(statuses);
    checkPrecondition(
        required.contains(orderStatus),
        "Order state must be in " + required + " for " + action + " action");
  }

  private void storeFailureMessages(final List<String> messages) {
    final List<String> filteredMessages =
        messages.stream()
                .filter(StringUtils::isNotBlank)
                .filter(m -> !failureMessages.contains(m))
                .distinct()
                .toList();

    if (!filteredMessages.isEmpty()) {
      failureMessages.addAll(filteredMessages);
    }
  }
}
