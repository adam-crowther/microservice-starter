package com.acroteq.ticketing.order.service.matchers;

import static java.util.Comparator.naturalOrder;

import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineOrderStatus;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.Flight;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderItemCommand;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class AirlineApprovalRequestMatcher extends TypeSafeMatcher<AirlineApprovalRequestMessage> {

  private final CreateOrderCommand request;
  private final PaymentRequestMessage paymentRequestMessage;
  private final AirlineEventMessage airline;

  public static AirlineApprovalRequestMatcher matches(final CreateOrderCommand request,
                                                      final PaymentRequestMessage paymentRequestMessage,
                                                      final AirlineEventMessage airline) {
    return new AirlineApprovalRequestMatcher(request, paymentRequestMessage, airline);
  }

  @Override
  protected boolean matchesSafely(final AirlineApprovalRequestMessage message) {

    return Objects.equals(message.getAirlineId(), request.getAirlineId())
           && Objects.equals(message.getAirlineOrderStatus(), AirlineOrderStatus.PAID)
           && Objects.equals(message.getOrderId(), paymentRequestMessage.getOrderId())
           && itemsMatch(message.getFlights(), request.getItems())
           && Objects.compare(message.getPriceAmount(), paymentRequestMessage.getValueAmount(), naturalOrder()) == 0
           && Objects.equals(message.getPriceCurrencyId(), paymentRequestMessage.getValueCurrencyId())
           && Objects.equals(message.getSagaId(), paymentRequestMessage.getSagaId());
  }


  private boolean itemsMatch(final List<Flight> order, final List<CreateOrderItemCommand> request) {
    return order.size() == request.size() && order.stream()
                                                  .allMatch(hasMatchingFlight(request));
  }

  private Predicate<Flight> hasMatchingFlight(final List<CreateOrderItemCommand> request) {
    return flight -> hasMatchingFlight(flight, request);
  }

  private boolean hasMatchingFlight(final Flight flight, final List<CreateOrderItemCommand> request) {
    return request.stream()
                  .anyMatch(isMatchingFlight(flight));
  }

  private Predicate<CreateOrderItemCommand> isMatchingFlight(final Flight flight) {
    return orderItem -> isMatchingFlight(orderItem, flight);
  }

  private boolean isMatchingFlight(final CreateOrderItemCommand orderItem, final Flight flight) {
    return Objects.equals(flight.getId(), orderItem.getFlightId()) && Objects.compare(flight.getQuantity(),
                                                                                      orderItem.getQuantity(),
                                                                                      naturalOrder()) == 0;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
    description.appendText(", payment request message ");
    description.appendValue(paymentRequestMessage);
    description.appendText(", airline ");
    description.appendValue(airline);
  }
}
