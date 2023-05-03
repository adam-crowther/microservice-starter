package com.acroteq.ticketing.application.mapper;

import static java.time.ZoneOffset.UTC;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

@Component
public class DateTimeMapper {

  public Instant convertToInstant(final OffsetDateTime offsetDateTime) {
    return offsetDateTime.toInstant();
  }

  public ZonedDateTime convertToZoned(final Instant instant) {
    return instant.atZone(UTC);
  }

  public OffsetDateTime convertToOffsetDateTime(final Instant instant) {
    return instant.atOffset(UTC);
  }
}
