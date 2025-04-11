package com.acroteq.ticketing.order.service.domain.entity;

import static com.acroteq.domain.valueobject.CashValue.ZERO;
import static com.acroteq.precondition.Precondition.checkPrecondition;

import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.FlightId;
import com.acroteq.ticketing.order.service.domain.exception.CodeMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.FlightPriceMismatchException;
import com.acroteq.ticketing.order.service.domain.exception.InvalidPriceException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class Flight extends ReplicatedEntity<FlightId> {

  @NonNull
  private final String code;
  @NonNull
  private final CashValue price;

  void validatePrice() {
    checkPrecondition(price.isGreaterThan(ZERO), InvalidPriceException::new);
  }

  public void validateCodeAndPriceEquality(final Flight other) {
    final String otherCode = other.getCode();
    checkPrecondition(code.equals(otherCode), CodeMismatchException::new, code, otherCode);

    final CashValue otherPrice = other.getPrice();
    checkPrecondition(price.equals(otherPrice), FlightPriceMismatchException::new, price, otherPrice);
  }
}
