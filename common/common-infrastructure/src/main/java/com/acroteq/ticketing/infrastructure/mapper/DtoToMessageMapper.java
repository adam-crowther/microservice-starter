package com.acroteq.ticketing.infrastructure.mapper;

import com.acroteq.ticketing.application.dto.Dto;
import org.apache.avro.specific.SpecificRecord;

public interface DtoToMessageMapper<DtoT extends Dto, MessageT extends SpecificRecord> {

  MessageT convertDtoToMessage(DtoT dto);
}
