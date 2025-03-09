package com.acroteq.domain.valueobject;

import static com.acroteq.ticketing.helper.StreamHelper.toSingleItem;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum OrderApprovalStatus {
  APPROVED("approved"), REJECTED("rejected");

  private final String value;

  public static Optional<OrderApprovalStatus> of(final String input) {
    return Stream.of(values())
                 .filter(hasValue(input))
                 .reduce(toSingleItem());
  }

  private static Predicate<OrderApprovalStatus> hasValue(final String input) {
    return eventType -> eventType.valueEquals(input);
  }

  private boolean valueEquals(final String input) {
    return value.equals(input);
  }

}
