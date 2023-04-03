package com.acroteq.food.ordering.system.domain.event;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import static com.acroteq.food.ordering.system.helper.DateHelper.nowInSystemDefaultTimezone;

@SuperBuilder
@Getter
@ToString
@EqualsAndHashCode
public class DomainEvent<T extends BaseEntity<?>> {

  @Builder.Default
  @NonNull
  private final OffsetDateTime createdDateTime = nowInSystemDefaultTimezone();
}
