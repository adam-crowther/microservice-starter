package com.acroteq.ticketing.domain.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public final class EmptyEvent extends DomainEvent<Void> {

  public static final EmptyEvent INSTANCE = EmptyEvent.builder()
                                                      .build();
}
