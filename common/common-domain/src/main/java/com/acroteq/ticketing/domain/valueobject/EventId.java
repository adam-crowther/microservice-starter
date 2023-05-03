package com.acroteq.ticketing.domain.valueobject;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class EventId {

  @NonNull Long offset;
  @NonNull Integer partition;

  public boolean isLessThan(final EventId other) {
    return partition.equals(other.getPartition()) && offset.compareTo(other.getOffset()) < 0;
  }

}
