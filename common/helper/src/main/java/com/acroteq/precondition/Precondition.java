package com.acroteq.precondition;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@NoArgsConstructor(access = PRIVATE)
public final class Precondition {

  public static <T extends RuntimeException> void checkPrecondition(final boolean expression,
                                                                    final Object errorMessage,
                                                                    final Function<String, T> exception) {
    if (!expression) {
      throw exception.apply(String.valueOf(errorMessage));
    }
  }

  public static <T extends RuntimeException> void checkPrecondition(final boolean expression,
                                                                    final Object errorMessage,
                                                                    final Function<String, T> exception,
                                                                    final Object... parameters) {
    if (!expression) {
      final String formattedString = String.format(String.valueOf(errorMessage), parameters);
      throw exception.apply(formattedString);
    }
  }

  public static <T extends RuntimeException> void checkPrecondition(final boolean expression,
                                                                    final Supplier<T> exception) {
    if (!expression) {
      throw exception.get();
    }
  }

  public static <S, T extends RuntimeException> void checkPrecondition(final boolean expression,
                                                                       final Function<S, T> exception,
                                                                       final S parameter) {
    if (!expression) {
      throw exception.apply(parameter);
    }
  }

  public static <R, S, T extends RuntimeException> void checkPrecondition(final boolean expression,
                                                                          final BiFunction<R, S, T> exception,
                                                                          final R parameter1,
                                                                          final S parameter2) {
    if (!expression) {
      throw exception.apply(parameter1, parameter2);
    }
  }
}
