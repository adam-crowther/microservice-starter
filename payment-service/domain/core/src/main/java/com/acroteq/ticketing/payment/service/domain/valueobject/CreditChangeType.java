package com.acroteq.ticketing.payment.service.domain.valueobject;

import static com.acroteq.ticketing.helper.StreamHelper.toSingleItem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CreditChangeType {
  PAYMENT("payment"), CREDIT_LIMIT_UPDATE("credit limit update");

  private final String value;

  public static Optional<CreditChangeType> of(final String input) {
    return Stream.of(values())
                 .filter(hasValue(input))
                 .reduce(toSingleItem());
  }

  private static Predicate<CreditChangeType> hasValue(final String input) {
    return eventType -> eventType.valueEquals(input);
  }

  private boolean valueEquals(final String input) {
    return value.equals(input);
  }
}
