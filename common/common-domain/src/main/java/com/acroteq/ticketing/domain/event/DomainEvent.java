package com.acroteq.ticketing.domain.event;

import static com.acroteq.ticketing.helper.DateHelper.nowInSystemDefaultTimezone;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class DomainEvent<T> {

  @Builder.Default
  @NonNull
  private final OffsetDateTime createdDateTime = nowInSystemDefaultTimezone();
}
