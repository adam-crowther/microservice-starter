package com.acroteq.food.ordering.system.domain.event;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

import static com.acroteq.food.ordering.system.helper.DateHelper.nowInSystemDefaultTimezone;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder(toBuilder = true)
public abstract class DomainEvent<T> {

  @Builder.Default
  @NonNull
  private final OffsetDateTime createdDateTime = nowInSystemDefaultTimezone();
}
