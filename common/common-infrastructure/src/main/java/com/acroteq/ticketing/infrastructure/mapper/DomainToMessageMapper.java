package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.entity.PrimaryEntity;
import org.apache.avro.specific.SpecificRecord;

public interface DomainToMessageMapper<EntityT extends PrimaryEntity<?>, MessageT extends SpecificRecord> {

  MessageT convertDomainToMessage(EntityT event);
}
