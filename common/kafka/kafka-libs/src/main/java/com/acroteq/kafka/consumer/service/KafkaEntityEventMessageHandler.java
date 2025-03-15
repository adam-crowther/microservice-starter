package com.acroteq.kafka.consumer.service;

import static com.acroteq.precondition.Precondition.checkPrecondition;
import static java.lang.Long.parseLong;

import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.kafka.consumer.exception.UnsupportedMessageTypeException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import java.util.Optional;
import java.util.function.Consumer;

@Slf4j
public class KafkaEntityEventMessageHandler extends KafkaMessageHandler {

  private final String messageType;
  private final MessageToDtoMapper<SpecificRecord, DataTransferObject> mapper;
  private final Consumer<DataTransferObject> createOrUpdateConsumer;
  private final Consumer<Long> deleteConsumer;

  @SuppressWarnings("unchecked")
  public <DtoT extends DataTransferObject> KafkaEntityEventMessageHandler(final String messageType,
                                                                          final MessageToDtoMapper<?
                                                                              extends SpecificRecord, DtoT> mapper,
                                                                          final Consumer<DtoT> createOrUpdateConsumer,
                                                                          final Consumer<Long> deleteConsumer) {
    super();
    this.messageType = messageType;
    // Upcast is OK
    // noinspection unchecked
    this.mapper = (MessageToDtoMapper<SpecificRecord, DataTransferObject>) mapper;
    this.createOrUpdateConsumer = (Consumer<DataTransferObject>) createOrUpdateConsumer;
    this.deleteConsumer = deleteConsumer;
  }

  @Override
  String getMessageType(final SpecificRecord message) {
    return Optional.ofNullable(message)
                   .map(SpecificRecord::getSchema)
                   .map(Schema::getName)
                   .orElse(messageType);
  }

  @Override
  void consumeMessage(final SpecificRecord message,
                      @NonNull final String key,
                      @NonNull final Integer partition,
                      @NonNull final Long offset) {
    if (message != null) {
      final String actualType = getMessageType(message);
      checkPrecondition(actualType.equals(messageType), actualType, UnsupportedMessageTypeException::new);
      createOrUpdateEntity(message, partition, offset);
    } else {
      deleteEntity(key);
    }
  }

  private void createOrUpdateEntity(final SpecificRecord message, final Integer partition, final Long offset) {
    final DataTransferObject dto = mapper.convertMessageToDto(message, partition, offset);
    createOrUpdateConsumer.accept(dto);
  }

  private void deleteEntity(final String key) {
    final Long id = parseLong(key);
    deleteConsumer.accept(id);
  }
}
