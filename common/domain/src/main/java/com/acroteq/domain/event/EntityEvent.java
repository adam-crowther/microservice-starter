package com.acroteq.domain.event;

import java.util.Optional;

public interface EntityEvent extends Event {

  Optional<Long> getId();

  Optional<Long> getVersion();
}
