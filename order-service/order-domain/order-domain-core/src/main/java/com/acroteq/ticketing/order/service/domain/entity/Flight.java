package com.acroteq.ticketing.order.service.domain.entity;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import com.acroteq.ticketing.domain.valueobject.CashValue;
import com.acroteq.ticketing.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.exception.FlightNumberMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.FlightPriceMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.InvalidPriceException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Flight extends BaseEntity<FlightId> {

  @NonNull
  private final String flightNumber;
  @NonNull
  private final CashValue price;

  void validatePrice() {
    checkPrecondition(price.isGreaterThanZero(), InvalidPriceException::new);
  }

  public void validateFlightNumberAndPriceEquality(final Flight other) {
    final String otherFlightNumber = other.getFlightNumber();
    checkPrecondition(flightNumber.equals(otherFlightNumber),
                      FlightNumberMismatchException::new,
                      flightNumber,
                      otherFlightNumber);

    final CashValue otherPrice = other.getPrice();
    checkPrecondition(price.equals(otherPrice), FlightPriceMismatchException::new, price, otherPrice);
  }
}
