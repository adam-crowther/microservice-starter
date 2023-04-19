package com.acroteq.ticketing.helper;

import com.acroteq.ticketing.exception.MoreThanOneItemInStreamException;

import java.util.function.BinaryOperator;

public final class StreamHelper {

  private StreamHelper() {
  }

  public static <T> BinaryOperator<T> toSingleItem() {
    return (item1, item2) -> {
      throw new MoreThanOneItemInStreamException();
    };
  }
}
