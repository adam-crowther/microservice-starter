package com.acroteq.application.mapper;

import com.acroteq.domain.entity.Entity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.domain.valueobject.EventId;
import org.apache.avro.specific.SpecificRecord;

public interface MessageToDomainMapper<MessageT extends SpecificRecord, EntityT extends Entity<? extends EntityId>> {

  EntityT convert(MessageT event, EventId eventId);
}
