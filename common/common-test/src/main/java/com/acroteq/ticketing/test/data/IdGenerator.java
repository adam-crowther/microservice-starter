package com.acroteq.ticketing.test.data;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;


@RequiredArgsConstructor
@Component
public class IdGenerator {

  private final AtomicLong id = new AtomicLong(0);

  public long nextId() {
    return id.getAndIncrement();
  }
}
