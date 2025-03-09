package com.acroteq.test.data;

import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.DAYS;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;


@RequiredArgsConstructor
@Component
public class RandomInstantGenerator {

  private static final Instant NOW = now();
  private static final Instant DEFAULT_UPPER_BOUND = NOW.plus(365, DAYS);
  private static final Instant DEFAULT_LOWER_BOUND = NOW.minus(365, DAYS);

  private final RandomIntegerGenerator randomIntegerGenerator;

  public Instant getBetween(final Instant lowerBound, final Instant upperBound) {
    final long startSeconds = lowerBound.getEpochSecond();
    final long endSeconds = upperBound.getEpochSecond();
    final long random = randomIntegerGenerator.getBetween(startSeconds, endSeconds);

    return Instant.ofEpochSecond(random);
  }

  public Instant get() {
    return getBetween(DEFAULT_LOWER_BOUND, DEFAULT_UPPER_BOUND);
  }

  public Instant getFutureBefore(final Instant upperBound) {
    return getBetween(NOW, upperBound);
  }

  public Instant getFuture() {
    return getBetween(NOW, DEFAULT_UPPER_BOUND);
  }

  public Instant getPastAfter(final Instant lowerBound) {
    return getBetween(lowerBound, NOW);
  }

  public Instant getPast() {
    return getBetween(DEFAULT_LOWER_BOUND, NOW);
  }
}
