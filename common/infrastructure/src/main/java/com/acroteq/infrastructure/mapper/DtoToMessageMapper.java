package com.acroteq.infrastructure.mapper;

import com.acroteq.application.dto.DataTransferObject;
import org.apache.avro.specific.SpecificRecord;

public interface DtoToMessageMapper<DtoT extends DataTransferObject, MessageT extends SpecificRecord> {

  MessageT convertDtoToMessage(DtoT dto);
}
