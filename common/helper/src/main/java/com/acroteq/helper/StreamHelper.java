package com.acroteq.helper;

import static lombok.AccessLevel.PRIVATE;

import com.acroteq.exception.MoreThanOneItemInStreamException;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

@NoArgsConstructor(access = PRIVATE)
public final class StreamHelper {

  public static <T> BinaryOperator<T> toSingleItem() {
    return (item1, item2) -> {
      throw new MoreThanOneItemInStreamException();
    };
  }

  public static <T> Consumer<T> withCounter(final BiConsumer<Integer, T> consumer) {
    final AtomicInteger counter = new AtomicInteger(0);
    return item -> consumer.accept(counter.getAndIncrement(), item);
  }
}
