package com.acroteq.ticketing.domain.event;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@ToString
@Builder
@Value
public class EmptyEvent implements Event {

  public static final EmptyEvent INSTANCE = EmptyEvent.builder()
                                                      .build();
}
