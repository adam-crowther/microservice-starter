package com.acroteq.ticketing.helper;

import static java.time.ZoneId.systemDefault;

import java.time.OffsetDateTime;

public final class DateHelper {

  private DateHelper() {
  }

  public static OffsetDateTime nowInSystemDefaultTimezone() {
    return OffsetDateTime.now(systemDefault());
  }
}
