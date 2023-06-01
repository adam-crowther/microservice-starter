package com.acroteq.ticketing.test.data;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_DOWN;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class RandomBigDecimalGenerator {

  private static final BigDecimal DEFAULT_UPPER_BOUND = BigDecimal.valueOf(10_000);
  private static final BigDecimal DEFAULT_LOWER_BOUND = BigDecimal.valueOf(-10_000);

  private static final int STANDARD_ROUNDING_SCALE = 2;

  private final RandomHolder randomHolder;

  public BigDecimal getBetween(final long lowerBound, final long upperBound) {
    return getBetween(lowerBound, upperBound, STANDARD_ROUNDING_SCALE);
  }

  public BigDecimal getBetween(final long lowerBound, final long upperBound, final int scale) {
    final long value = randomHolder.getNextLong(lowerBound, upperBound);
    return BigDecimal.valueOf(value)
                     .setScale(scale, HALF_DOWN);
  }

  public BigDecimal getBetween(final double lowerBound, final double upperBound) {
    return getBetween(lowerBound, upperBound, STANDARD_ROUNDING_SCALE);
  }

  public BigDecimal getBetween(final double lowerBound, final double upperBound, final int scale) {
    final double value = randomHolder.getNextDouble(lowerBound, upperBound);
    return BigDecimal.valueOf(value)
                     .setScale(scale, HALF_DOWN);
  }

  public BigDecimal getBetween(final BigDecimal lowerBound, final BigDecimal upperBound) {
    return getBetween(lowerBound, upperBound, STANDARD_ROUNDING_SCALE);
  }

  public BigDecimal getBetween(final BigDecimal lowerBound, final BigDecimal upperBound, final int scale) {
    return getBetween(lowerBound.doubleValue(), upperBound.doubleValue()).setScale(scale, HALF_DOWN);
  }

  public BigDecimal get() {
    return getBetween(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
  }

  public BigDecimal getPositiveWithUpperBound(final BigDecimal upperBound) {
    return getBetween(ZERO, upperBound);
  }

  public BigDecimal getPositiveWithUpperBound(final BigDecimal upperBound, final int scale) {
    return getBetween(ZERO, upperBound, scale);
  }

  public BigDecimal getPositive() {
    return getBetween(ZERO, DEFAULT_UPPER_BOUND);
  }

  public BigDecimal getNegativeWithLowerBound(final BigDecimal lowerBound) {
    return getBetween(lowerBound, ZERO);
  }

  public BigDecimal getNegativeWithLowerBound(final BigDecimal lowerBound, final int scale) {
    return getBetween(lowerBound, ZERO, scale);
  }

  public BigDecimal getNegative() {
    return getBetween(DEFAULT_LOWER_BOUND, ZERO);
  }
}
