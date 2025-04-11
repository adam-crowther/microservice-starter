package com.acroteq.infrastructure.mapper;

import com.acroteq.domain.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface EventToMessageMapper<EventT extends Event, MessageT extends SpecificRecord> {

  MessageT convert(EventT event);
}
