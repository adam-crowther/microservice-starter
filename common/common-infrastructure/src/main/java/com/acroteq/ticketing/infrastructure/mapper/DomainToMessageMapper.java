package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.entity.BaseEntity;
import org.apache.avro.specific.SpecificRecord;

public interface DomainToMessageMapper<EntityT extends BaseEntity<?>, MessageT extends SpecificRecord> {

  MessageT convertDomainToMessage(EntityT event);
}
