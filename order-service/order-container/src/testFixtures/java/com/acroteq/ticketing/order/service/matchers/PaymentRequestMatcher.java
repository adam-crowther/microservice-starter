package com.acroteq.ticketing.order.service.matchers;

import static java.math.BigDecimal.ZERO;

import com.acroteq.ticketing.helper.StreamHelper;
import com.acroteq.ticketing.kafka.airline.avro.model.AirlineEventMessage;
import com.acroteq.ticketing.kafka.airline.avro.model.Flight;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.order.service.client.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.client.model.CreateOrderItemCommand;
import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class PaymentRequestMatcher extends TypeSafeMatcher<PaymentRequestMessage> {

  private final CreateOrderCommand request;
  private final long orderId;
  private final AirlineEventMessage airline;

  public static PaymentRequestMatcher matches(final CreateOrderCommand request,
                                              final long orderId,
                                              final AirlineEventMessage airline) {
    return new PaymentRequestMatcher(request, orderId, airline);
  }

  private static Flight selectFlight(final AirlineEventMessage airline, final long flightId) {
    return airline.getFlights()
                  .stream()
                  .filter(hasFlightId(flightId))
                  .reduce(StreamHelper.toSingleItem())
                  .orElseThrow(() -> new IllegalArgumentException("Flight not found: " + flightId));
  }

  private static Predicate<Flight> hasFlightId(final long flightId) {
    return flight -> hasFlightId(flight, flightId);
  }

  private static boolean hasFlightId(final Flight flight, final long flightId) {
    return flight.getId() == flightId;
  }

  @Override
  protected boolean matchesSafely(final PaymentRequestMessage message) {
    final BigDecimal requestValueAmount = calculateValue(request, airline);
    final String requestValueCurrencyId = calculateCurrencyId(request, airline);

    return Objects.equals(message.getValueAmount(), requestValueAmount)
           && Objects.equals(message.getCustomerId(),
                             request.getCustomerId())
           && Objects.equals(message.getValueCurrencyId(), requestValueCurrencyId)
           && Objects.equals(message.getOrderId(), orderId);
  }

  private BigDecimal calculateValue(final CreateOrderCommand request, final AirlineEventMessage airline) {
    return request.getItems()
                  .stream()
                  .map(calculateValue(airline))
                  .reduce(ZERO, BigDecimal::add);
  }

  private Function<CreateOrderItemCommand, BigDecimal> calculateValue(final AirlineEventMessage airline) {
    return item -> calculateValue(item, airline);
  }

  private BigDecimal calculateValue(final CreateOrderItemCommand item, final AirlineEventMessage airline) {
    final long flightId = item.getFlightId();
    final Flight flight = selectFlight(airline, flightId);

    final BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
    return flight.getPriceAmount()
                 .multiply(quantity);
  }

  private String calculateCurrencyId(final CreateOrderCommand request, final AirlineEventMessage airline) {
    return request.getItems()
                  .stream()
                  .map(calculateCurrencyId(airline))
                  .distinct()
                  .reduce(StreamHelper.toSingleItem())
                  .orElseThrow(() -> new IllegalArgumentException("Items must have same currency"));
  }

  private Function<CreateOrderItemCommand, String> calculateCurrencyId(final AirlineEventMessage airline) {
    return item -> calculateCurrencyId(item, airline);
  }

  private String calculateCurrencyId(final CreateOrderItemCommand item, final AirlineEventMessage airline) {
    final long flightId = item.getFlightId();
    final Flight flight = selectFlight(airline, flightId);

    return flight.getPriceCurrencyId();
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText("has matching values according to request ");
    description.appendValue(request);
    description.appendText(", orderId ");
    description.appendValue(orderId);
    description.appendText(", airline ");
    description.appendValue(airline);
  }
}
