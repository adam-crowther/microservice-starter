package com.acroteq.food.ordering.system.common.application.mapper;

import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@Mapper
public class DateTimeMapper {

  public Instant convert(final OffsetDateTime offsetDateTime) {
    return offsetDateTime.toInstant();
  }

  public ZonedDateTime convert(final Instant instant) {
    return instant.atZone(UTC);
  }
}
