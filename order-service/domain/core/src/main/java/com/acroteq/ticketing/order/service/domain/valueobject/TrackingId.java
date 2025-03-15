package com.acroteq.ticketing.order.service.domain.valueobject;

import static java.util.UUID.randomUUID;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class TrackingId {

  private final UUID value;

  public static TrackingId of(final UUID id) {
    return TrackingId.builder()
                     .value(id)
                     .build();
  }

  public static TrackingId random() {
    return of(randomUUID());
  }
}
