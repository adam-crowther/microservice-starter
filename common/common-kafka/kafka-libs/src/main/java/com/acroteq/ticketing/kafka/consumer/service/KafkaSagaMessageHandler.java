package com.acroteq.ticketing.kafka.consumer.service;


import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.kafka.consumer.exception.UnsupportedMessageTypeException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class KafkaSagaMessageHandler extends KafkaMessageHandler {

  private static final String UNSUPPORTED_MESSAGE_TYPE = "unsupported-message-type";

  private final Map<String, MessageConsumer<? extends SpecificRecord, ? extends DataTransferObject>> consumers;

  public static KafkaSagaMessageHandlerBuilder builder() {
    return new KafkaSagaMessageHandlerBuilder();
  }

  @Override
  void consumeMessage(final SpecificRecord message,
                      final String key,
                      @NonNull final Integer partition,
                      @NonNull final Long offset) {
    final String messageType = getMessageType(message);
    final MessageConsumer<SpecificRecord, DataTransferObject> consumer = getConsumer(messageType);
    consumer.consumeMessage(message, partition, offset);
  }

  @SuppressWarnings("unchecked")
  private MessageConsumer<SpecificRecord, DataTransferObject> getConsumer(final String messageType) {
    // Upcast is OK
    // noinspection unchecked
    return Optional.of(messageType)
                   .map(consumers::get)
                   .map(handler -> (MessageConsumer<SpecificRecord, DataTransferObject>) handler)
                   .orElseThrow(() -> new UnsupportedMessageTypeException(messageType));
  }

  @Override
  String getMessageType(final SpecificRecord message) {
    return Optional.ofNullable(message)
                   .map(SpecificRecord::getSchema)
                   .map(Schema::getName)
                   .orElse(UNSUPPORTED_MESSAGE_TYPE);
  }
}



