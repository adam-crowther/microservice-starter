package com.acroteq.ticketing.domain.entity;

import com.acroteq.ticketing.domain.valueobject.EntityId;
import com.acroteq.ticketing.domain.valueobject.EventId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@SuperBuilder(toBuilder = true)
public abstract class ReplicatedEntity<IdT extends EntityId> implements Entity<IdT> {

  @EqualsAndHashCode.Include
  @NonNull
  private final IdT id;

  @NonNull
  private final Long version;

  @NonNull
  private final EventId eventId;

  public boolean isFromTheSameEventAs(final ReplicatedEntity<IdT> other) {
    return eventId.equals(other.getEventId());
  }

  public boolean isFromAnEarlierEventThan(final ReplicatedEntity<IdT> other) {
    return eventId.isLessThan(other.getEventId());
  }
}
