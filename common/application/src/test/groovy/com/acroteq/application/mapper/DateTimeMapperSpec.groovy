package com.acroteq.application.mapper

import groovy.transform.CompileDynamic
import spock.lang.Specification

import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

import static java.time.ZoneOffset.UTC
import static java.time.temporal.ChronoUnit.HOURS

@CompileDynamic
class DateTimeMapperSpec extends Specification {

  static final long EPOCH_MILLIS_AT_UTC = 1683832517
  static final long EPOCH_SECOND_AT_UTC = 1683832

  static final int EPOCH_DIFFERENCE_NANOS = (int) (EPOCH_MILLIS_AT_UTC - (EPOCH_SECOND_AT_UTC * 1000)) * (10**6)  // codenarc-disable-line SpaceAroundOperator

  static final int TIMEZONE_DIFFERENCE_HOURS = 2
  static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(TIMEZONE_DIFFERENCE_HOURS)
  static final ZoneId ZONE_ID = ZoneId.ofOffset('', ZONE_OFFSET)

  static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.ofEpochSecond(EPOCH_SECOND_AT_UTC, EPOCH_DIFFERENCE_NANOS, UTC)
  static final OffsetDateTime OFFSET_DATE_TIME_AT_UTC = OffsetDateTime.of(LOCAL_DATE_TIME, UTC)
  static final OffsetDateTime OFFSET_DATE_TIME_AT_ZONE = OffsetDateTime.of(LOCAL_DATE_TIME, ZONE_OFFSET).plus(TIMEZONE_DIFFERENCE_HOURS, HOURS)

  static final ZonedDateTime ZONED_DATE_TIME_AT_UTC = ZonedDateTime.of(LOCAL_DATE_TIME, UTC)
  static final ZonedDateTime ZONED_DATE_TIME_AT_ZONE = ZonedDateTime.of(LOCAL_DATE_TIME, ZONE_ID).plus(TIMEZONE_DIFFERENCE_HOURS, HOURS)

  static final Instant INSTANT = Instant.ofEpochMilli(EPOCH_MILLIS_AT_UTC)

  DateTimeMapper dateTimeMapper = new DateTimeMapper()

  def 'converts OffsetDateTime to Instant correctly'() {
    when:
    def instant = dateTimeMapper.convertToInstant(input)

    then:
    instant == expected

    where:
    input                    || expected
    OFFSET_DATE_TIME_AT_UTC  || INSTANT
    OFFSET_DATE_TIME_AT_ZONE || INSTANT
    null                     || null
  }

  def 'converts Instant to ZonedDateTime at UTC correctly'() {
    when:
    def zonedDateTime = dateTimeMapper.convertToZonedAtUtc(input)

    then:
    zonedDateTime == expected

    where:
    input   || expected
    INSTANT || ZONED_DATE_TIME_AT_UTC
    null    || null
  }

  def 'converts Instant to ZonedDateTime at a given timezone correctly'() {
    when:
    def zonedDateTime = dateTimeMapper.convertToZoned(input, timeZone)

    then:
    zonedDateTime == expected

    where:
    input   | timeZone || expected
    INSTANT | UTC      || ZONED_DATE_TIME_AT_UTC
    INSTANT | ZONE_ID  || ZONED_DATE_TIME_AT_ZONE
    null    | UTC      || null
    null    | ZONE_ID  || null
  }

  def 'converts Instant to OffsetDateTime at UTC correctly'() {
    when:
    def offsetDateTime = dateTimeMapper.convertToOffsetAtUtc(input)

    then:
    offsetDateTime == expected

    where:
    input   || expected
    INSTANT || OFFSET_DATE_TIME_AT_UTC
    null    || null
  }

  def 'converts Instant to OffsetDateTime at a given timezone correctly'() {
    when:
    def offsetDateTime = dateTimeMapper.convertToOffset(input, timeZoneOffset)

    then:
    offsetDateTime == expected

    where:
    input   | timeZoneOffset || expected
    INSTANT | UTC            || OFFSET_DATE_TIME_AT_UTC
    INSTANT | ZONE_OFFSET    || OFFSET_DATE_TIME_AT_ZONE
    null    | UTC            || null
    null    | ZONE_OFFSET    || null
  }
}
