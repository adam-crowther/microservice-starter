package com.acroteq.test.data;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomIntegerGenerator {

  private static final int DEFAULT_UPPER_BOUND = 10_000;
  private static final int DEFAULT_LOWER_BOUND = -10_000;

  private final RandomHolder randomHolder;

  public int getBetween(final double lowerBound, final double upperBound) {
    final double value = randomHolder.getNextDouble(lowerBound, upperBound);
    return (int) (value < 0 ? ceil(value) : floor(value));
  }

  public int getBetween(final int lowerBound, final int upperBound) {
    return randomHolder.getNextInteger(lowerBound, upperBound);
  }

  public int get() {
    return randomHolder.getNextInteger(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
  }

  public int getPositiveWithUpperBound(final int upperBound) {
    return getBetween(0, upperBound);
  }

  public int getPositive() {
    return getBetween(0, DEFAULT_UPPER_BOUND);
  }

  public int getNegativeWithLowerBound(final int lowerBound) {
    return getBetween(lowerBound, 0);
  }

  public int getNegative() {
    return getBetween(DEFAULT_LOWER_BOUND, 0);
  }
}
