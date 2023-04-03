package com.acroteq.food.ordering.system.helper;

import com.acroteq.food.ordering.system.exception.MoreThanOneItemInStreamException;

import java.util.function.BinaryOperator;

public final class StreamHelper {

  private StreamHelper() {
  }

  public static <T> BinaryOperator<T> toSingleItem() {
    return (s, t) -> { throw new MoreThanOneItemInStreamException(); };
  }
}
