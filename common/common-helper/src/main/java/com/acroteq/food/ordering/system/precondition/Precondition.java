package com.acroteq.food.ordering.system.precondition;

import java.util.function.Function;

public final class Precondition {

  private Precondition() {
  }

  public static <T extends RuntimeException> void checkPrecondition(
      final boolean expression, final Object errorMessage, final Function<String, T> exception) {
    if (!expression) {
      throw exception.apply(String.valueOf(errorMessage));
    }
  }

  public static <T extends RuntimeException> void checkPrecondition(
      final boolean expression,
      final Object errorMessage,
      final Function<String, T> exception,
      final Object... parameters) {
    if (!expression) {
      final String formattedString = String.format(String.valueOf(errorMessage), parameters);
      throw exception.apply(formattedString);
    }
  }

  public static <S, T extends RuntimeException> void checkPrecondition(
      final boolean expression,
      final Function<S, T> exception,
      final S parameter) {
    if (!expression) {
      throw exception.apply(parameter);
    }
  }
}
