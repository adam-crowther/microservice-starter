package com.acroteq.test.data;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import org.junit.jupiter.api.Test;

class RandomStringGeneratorTest {

  private final RandomHolder randomHolder = new RandomHolder();
  private final RandomIntegerGenerator randomIntegerGenerator = new RandomIntegerGenerator(randomHolder);
  private final RandomStringGenerator randomStringGenerator = new RandomStringGenerator(randomIntegerGenerator);

  @Test
  void testGetRandomStringWithLength() {
    final String string = randomStringGenerator.getRandomString(4);
    assertThat(string.length(), is(lessThanOrEqualTo(4)));
  }
}