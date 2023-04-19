package com.acroteq.ticketing.application.mapper;

import static java.time.ZoneOffset.UTC;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Component
public class DateTimeMapper {

  public Instant convert(final OffsetDateTime offsetDateTime) {
    return offsetDateTime.toInstant();
  }

  public ZonedDateTime convert(final Instant instant) {
    return instant.atZone(UTC);
  }
}
