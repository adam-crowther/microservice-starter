package com.acroteq.ticketing.infrastructure.mapper;

import org.apache.avro.specific.SpecificRecord;

public interface MessageToDtoMapper<MessageT extends SpecificRecord, DtoT> {

  DtoT convertMessageToDto(MessageT message, Integer partition, Long offset);
}
