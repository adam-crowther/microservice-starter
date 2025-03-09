package com.acroteq.ticketing.order.service.matchers;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.client.matchers.OrderMatcher;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.Order;
import com.acroteq.ticketing.order.service.client.model.OrderStatus;
import org.hamcrest.Matcher;

import java.util.UUID;

public final class OrderServiceMatchers {

  public static Matcher<AirlineApprovalRequestMessage> matches(final CreateOrderCommand request,
                                                               final PaymentRequestMessage paymentRequestMessage,
                                                               final AirlineEventMessage airline) {
    return AirlineApprovalRequestMatcher.matches(request, paymentRequestMessage, airline);
  }

  public static Matcher<Order> matches(final CreateOrderCommand request,
                                       final OrderStatus state,
                                       final UUID trackingId) {
    return OrderMatcher.matches(request, state, trackingId);
  }

  public static Matcher<PaymentRequestMessage> matches(final CreateOrderCommand request,
                                                       final long orderId,
                                                       final AirlineEventMessage airline) {
    return PaymentRequestMatcher.matches(request, orderId, airline);
  }

  private OrderServiceMatchers() {
  }
}
