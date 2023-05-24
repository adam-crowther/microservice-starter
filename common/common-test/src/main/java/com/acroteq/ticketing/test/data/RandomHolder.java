package com.acroteq.ticketing.test.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.springframework.stereotype.Component;

import java.util.Random;

@SuppressFBWarnings("PREDICTABLE_RANDOM")
@Component
public class RandomHolder {

  private static final long SEED = 42L;

  private final Random random = new Random(SEED);

  public void resetSeed() {
    random.setSeed(SEED);
  }

  public double getNextDouble(final double lowerBound, final double upperBound) {
    return random.nextDouble(lowerBound, upperBound);
  }

  public int getNextInteger(final int lowerBound, final int upperBound) {
    return random.nextInt(lowerBound, upperBound);
  }

  public long getNextLong(final long lowerBound, final long upperBound) {
    return random.nextLong(lowerBound, upperBound);
  }
}
