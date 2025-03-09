package com.acroteq.ticketing.order.service.domain;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Flight;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.entity.OrderItem;
import com.acroteq.ticketing.order.service.domain.exception.AirlineNotActiveException;
import com.acroteq.ticketing.order.service.domain.exception.FlightNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {

  @Override
  public void validate(final Order order, final Airline airline) {
    validateAirline(airline);
    validateOrderFlights(order, airline);

    order.validateOrder();
    log.info("Order {} validated", order.getId());
  }

  private void validateAirline(final Airline airline) {
    checkPrecondition(airline.isActive(), AirlineNotActiveException::new, airline.getId());
  }

  private void validateOrderFlights(final Order order, final Airline airline) {
    final Map<FlightId, Flight> flightMap = airline.getFlightMap();
    order.getItems()
         .stream()
         .map(OrderItem::getFlight)
         .forEach(validateOrderFlight(flightMap));
  }

  private Consumer<Flight> validateOrderFlight(final Map<FlightId, Flight> flightMap) {
    return flight -> validateOrderFlight(flightMap, flight);
  }

  private void validateOrderFlight(final Map<FlightId, Flight> flightMap, final Flight currentFlight) {
    Optional.of(currentFlight)
            .map(Flight::getId)
            .map(flightMap::get)
            .ifPresentOrElse(currentFlight::validateFlightNumberAndPriceEquality,
                             throwFlightNotFoundException(currentFlight));
  }

  private Runnable throwFlightNotFoundException(final Flight currentFlight) {
    return () -> {
      throw new FlightNotFoundException(currentFlight.getId());
    };
  }

  @Override
  public Order payOrder(final Order order) {
    final Order paidOrder = order.pay();
    log.info("Order {} is paid", paidOrder.getId());
    return paidOrder;
  }

  @Override
  public Order approveOrder(final Order order) {
    final Order approvedOrder = order.approve();
    log.info("Order {} is approved", approvedOrder.getId());
    return approvedOrder;
  }

  @Override
  public Order cancelOrderPayment(final Order order, final ValidationResult cancelResult) {
    final Order cancelledOrder = order.initCancel(cancelResult);
    log.info("Order {} cancel requested", cancelledOrder.getId());
    return cancelledOrder;
  }

  @Override
  public Order cancelOrder(final Order order, final ValidationResult cancelResult) {
    final Order cancelledOrder = order.cancel(cancelResult);
    log.info("Order {} cancelled", cancelledOrder.getId());
    return cancelledOrder;
  }
}
