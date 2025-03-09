package com.acroteq.infrastructure.mapper;

import com.acroteq.domain.entity.PrimaryEntity;
import org.apache.avro.specific.SpecificRecord;

public interface DomainToMessageMapper<EntityT extends PrimaryEntity<?>, MessageT extends SpecificRecord> {

  MessageT convertDomainToMessage(EntityT event);
}
