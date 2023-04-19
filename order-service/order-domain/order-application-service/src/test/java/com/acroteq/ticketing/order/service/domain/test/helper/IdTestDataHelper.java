package com.acroteq.ticketing.order.service.domain.test.helper;

import java.util.concurrent.atomic.AtomicLong;

public final class IdTestDataHelper {

  private static final AtomicLong CURRENT_ID = new AtomicLong(1L);

  private IdTestDataHelper() {
  }

  public static Long nextId() {
    return CURRENT_ID.getAndIncrement();
  }
}
