package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.event.Event;
import org.apache.avro.specific.SpecificRecord;

public interface EventToMessageMapper<EventT extends Event, MessageT extends SpecificRecord> {

  MessageT convertEventToMessage(EventT event);
}
