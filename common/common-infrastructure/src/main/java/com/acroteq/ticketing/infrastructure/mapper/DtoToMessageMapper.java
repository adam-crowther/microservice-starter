package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import org.apache.avro.specific.SpecificRecord;

public interface DtoToMessageMapper<DtoT extends DataTransferObject, MessageT extends SpecificRecord> {

  MessageT convertDtoToMessage(DtoT dto);
}
