package com.acroteq.food.ordering.system.helper;

import java.time.OffsetDateTime;

import static java.time.ZoneId.systemDefault;

public final class DateHelper {

  private DateHelper() {
  }

  public static final OffsetDateTime nowInSystemDefaultTimezone() {
    return OffsetDateTime.now(systemDefault());
  }
}
