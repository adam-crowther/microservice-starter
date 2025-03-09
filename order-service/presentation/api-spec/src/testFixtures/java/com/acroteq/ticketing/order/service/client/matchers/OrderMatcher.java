package com.acroteq.ticketing.order.service.client.matchers;

import static java.util.Comparator.naturalOrder;

import com.acroteq.ticketing.order.service.client.model.Address;
import com.acroteq.ticketing.order.service.client.model.CreateAddressCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderItemCommand;
import com.acroteq.ticketing.order.service.client.model.Order;
import com.acroteq.ticketing.order.service.client.model.OrderItem;
import com.acroteq.ticketing.order.service.client.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class OrderMatcher extends TypeSafeMatcher<Order> {

  private final CreateOrderCommand request;
  private final OrderStatus state;
  private final UUID trackingId;

  public static OrderMatcher matches(final CreateOrderCommand request, final OrderStatus state, final UUID trackingId) {
    return new OrderMatcher(request, state, trackingId);
  }

  @Override
  protected boolean matchesSafely(final Order order) {

    return Objects.equals(order.getStatus(), state)
           && Objects.equals(order.getTrackingId(), trackingId)
           && Objects.equals(order.getAirlineId(), request.getAirlineId())
           && Objects.equals(order.getCustomerId(), request.getCustomerId())
           && addressesMatch(order.getAddress(), request.getAddress())
           && itemsMatch(order.getItems(), request.getItems());
  }

  private boolean addressesMatch(final Address order, final CreateAddressCommand request) {
    return Objects.equals(order.getCity(), request.getCity())
           && Objects.equals(order.getStreet(), request.getStreet())
           && Objects.equals(order.getPostalCode(), request.getPostalCode());
  }

  private boolean itemsMatch(final List<OrderItem> order, final List<CreateOrderItemCommand> request) {
    return order.size() == request.size() && order.stream()
                                                  .allMatch(hasMatchingItem(request));
  }

  private Predicate<OrderItem> hasMatchingItem(final List<CreateOrderItemCommand> request) {
    return orderItem -> hasMatchingItem(orderItem, request);
  }

  private boolean hasMatchingItem(final OrderItem orderItem, final List<CreateOrderItemCommand> request) {
    return request.stream()
                  .anyMatch(isMatchingItem(orderItem));
  }

  private Predicate<CreateOrderItemCommand> isMatchingItem(final OrderItem orderItem) {
    return requestItem -> isMatchingItem(requestItem, orderItem);
  }

  private boolean isMatchingItem(final CreateOrderItemCommand requestItem, final OrderItem orderItem) {
    return Objects.equals(requestItem.getFlightId(), orderItem.getFlightId())
           && Objects.compare(requestItem.getQuantity(), orderItem.getQuantity(), naturalOrder()) == 0;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
    description.appendText(", state ");
    description.appendValue(state);
    description.appendText(", trackingId ");
    description.appendValue(trackingId);
  }
}
