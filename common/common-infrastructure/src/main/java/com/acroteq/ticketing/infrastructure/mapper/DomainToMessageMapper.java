package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.domain.entity.MasterEntity;
import org.apache.avro.specific.SpecificRecord;

public interface DomainToMessageMapper<EntityT extends MasterEntity<?>, MessageT extends SpecificRecord> {

  MessageT convertDomainToMessage(EntityT event);
}
