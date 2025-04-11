package com.acroteq.application.mapper;

import static java.time.ZoneOffset.UTC;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.function.Function;

@Component
public class DateTimeMapper {

  @Nullable
  public Instant convertToInstant(@Nullable final OffsetDateTime offsetDateTime) {
    return Optional.ofNullable(offsetDateTime)
                   .map(OffsetDateTime::toInstant)
                   .orElse(null);
  }

  @Nullable
  public ZonedDateTime convertToZonedAtUtc(@Nullable final Instant instant) {
    return convertToZoned(instant, UTC);
  }

  @Nullable
  public ZonedDateTime convertToZoned(@Nullable final Instant instant, @NotNull final ZoneId zone) {
    return Optional.ofNullable(instant)
                   .map(toZone(zone))
                   .orElse(null);
  }

  private Function<Instant, ZonedDateTime> toZone(final ZoneId zone) {
    return instant -> instant.atZone(zone);
  }

  @Nullable
  public OffsetDateTime convertToOffsetAtUtc(@Nullable final Instant instant) {
    return convertToOffset(instant, UTC);
  }

  @Nullable
  public OffsetDateTime convertToOffset(@Nullable final Instant instant, @NotNull final ZoneOffset offset) {
    return Optional.ofNullable(instant)
                   .map(toOffset(offset))
                   .orElse(null);
  }

  private Function<Instant, OffsetDateTime> toOffset(final ZoneOffset offset) {
    return instant -> instant.atOffset(offset);
  }

  @Nullable
  public ZoneId convertToZoneId(@Nullable final String timezone) {
    return Optional.ofNullable(timezone)
                   .map(ZoneId::of)
                   .orElse(null);
  }

  @Nullable
  public String convertZoneIdToString(@Nullable final ZoneId zoneId) {
    return Optional.ofNullable(zoneId)
                   .map(ZoneId::getId)
                   .orElse(null);
  }

  @Nullable
  public Integer convertLocalTimeToInt(@Nullable final LocalTime localTime) {
    return Optional.ofNullable(localTime)
                   .map(LocalTime::toNanoOfDay)
                   .map(n -> n / 1000)
                   .map(Long::intValue)
                   .orElse(null);
  }

  @Nullable
  public LocalTime convertIntToLocalTime(@Nullable final Integer time) {
    return Optional.ofNullable(time)
                   .map(Integer::longValue)
                   .map(m -> m * 1000)
                   .map(LocalTime::ofNanoOfDay)
                   .orElse(null);
  }

  @Nullable
  public Integer convertDayOfWeekToInt(@Nullable final DayOfWeek day) {
    return Optional.ofNullable(day)
                   .map(DayOfWeek::getValue)
                   .orElse(null);
  }

  @Nullable
  public DayOfWeek convertIntToDayOfWeek(@Nullable final Integer day) {
    return Optional.ofNullable(day)
                   .map(DayOfWeek::of)
                   .orElse(null);
  }
}
