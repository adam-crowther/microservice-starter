package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.event.DomainEvent;
import org.apache.avro.specific.SpecificRecord;

public interface EventToMessageMapper<EventT extends DomainEvent<?>, MessageT extends SpecificRecord> {

  MessageT convertEventToMessage(EventT event);
}
