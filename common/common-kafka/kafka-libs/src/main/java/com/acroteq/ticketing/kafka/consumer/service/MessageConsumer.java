package com.acroteq.ticketing.kafka.consumer.service;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;

import java.util.function.Consumer;

@RequiredArgsConstructor
class MessageConsumer<MessageT extends SpecificRecord, DtoT extends DataTransferObject> {

  @NonNull
  private final MessageToDtoMapper<MessageT, DtoT> mapper;
  @NonNull
  private final Consumer<DtoT> consumer;

  void consumeMessage(final MessageT message, final Integer partition, final Long offset) {
    final DtoT dto = mapper.convertMessageToDto(message, partition, offset);
    consumer.accept(dto);
  }
}
