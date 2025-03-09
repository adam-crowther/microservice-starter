package com.acroteq.test.data;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomLongGenerator {

  private static final long DEFAULT_UPPER_BOUND = 10_000L;
  private static final long DEFAULT_LOWER_BOUND = -10_000L;

  private final RandomHolder randomHolder;

  public long getBetween(final double lowerBound, final double upperBound) {
    final double value = randomHolder.getNextDouble(lowerBound, upperBound);

    return (long) (value < 0 ? ceil(value) : floor(value));
  }

  public long getBetween(final long lowerBound, final long upperBound) {
    return randomHolder.getNextLong(lowerBound, upperBound);
  }

  public long get() {
    return randomHolder.getNextLong(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
  }

  public long getPositiveWithUpperBound(final long upperBound) {
    return getBetween(0, upperBound);
  }

  public long getPositive() {
    return getBetween(0, DEFAULT_UPPER_BOUND);
  }

  public long getNegativeWithLowerBound(final long lowerBound) {
    return getBetween(lowerBound, 0);
  }

  public long getNegative() {
    return getBetween(DEFAULT_LOWER_BOUND, 0);
  }
}
