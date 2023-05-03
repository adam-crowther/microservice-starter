package com.acroteq.ticketing.domain.event;

import java.util.Optional;

public interface EntityEvent extends Event {

  Optional<Long> getId();
}
