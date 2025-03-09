package com.acroteq.test.data;

import static org.apache.commons.math3.util.Precision.round;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RandomDoubleGenerator {

  private static final double DEFAULT_UPPER_BOUND = 10_000.0;
  private static final double DEFAULT_LOWER_BOUND = -10_000.0;

  private static final int STANDARD_ROUNDING_SCALE = 2;
  private static final double ZERO = 0.0;

  private final RandomHolder randomHolder;

  public double getBetween(final long lowerBound, final long upperBound) {
    return getBetween(lowerBound, upperBound, STANDARD_ROUNDING_SCALE);
  }

  public double getBetween(final long lowerBound, final long upperBound, final int scale) {
    final long value = randomHolder.getNextLong(lowerBound, upperBound);
    return round(value, scale);
  }

  public double getBetween(final double lowerBound, final double upperBound) {
    return getBetween(lowerBound, upperBound, STANDARD_ROUNDING_SCALE);
  }

  public double getBetween(final double lowerBound, final double upperBound, final int scale) {
    final double value = randomHolder.getNextDouble(lowerBound, upperBound);
    return round(value, scale);
  }

  public double get() {
    return getBetween(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
  }

  public double getPositiveWithUpperBound(final double upperBound) {
    return getBetween(ZERO, upperBound);
  }

  public double getPositiveWithUpperBound(final double upperBound, final int scale) {
    return getBetween(ZERO, upperBound, scale);
  }

  public double getPositive() {
    return getBetween(ZERO, DEFAULT_UPPER_BOUND);
  }

  public double getNegativeWithLowerBound(final double lowerBound) {
    return getBetween(lowerBound, ZERO);
  }

  public double getNegativeWithLowerBound(final double lowerBound, final int scale) {
    return getBetween(lowerBound, ZERO, scale);
  }

  public double getNegative() {
    return getBetween(DEFAULT_LOWER_BOUND, ZERO);
  }
}
