package com.acroteq.domain.valueobject;

import static com.acroteq.ticketing.helper.StreamHelper.toSingleItem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
  PENDING("pending"), COMPLETED("completed"), CANCELLED("cancelled"), FAILED("failed");

  private final String value;

  public static Optional<PaymentStatus> of(final String input) {
    return Stream.of(values())
                 .filter(hasValue(input))
                 .reduce(toSingleItem());
  }

  private static Predicate<PaymentStatus> hasValue(final String input) {
    return eventType -> eventType.valueEquals(input);
  }

  private boolean valueEquals(final String input) {
    return value.equals(input);
  }
}
