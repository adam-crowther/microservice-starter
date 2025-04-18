package com.acroteq.kafka.consumer.service;

import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.application.dto.DataTransferObject;
import com.acroteq.infrastructure.mapper.MessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class KafkaSagaMessageHandlerBuilder {

  private final Map<String, MessageConsumer<? extends SpecificRecord, ? extends DataTransferObject>> consumers =
      new ConcurrentHashMap<>();

  public <MessageT extends SpecificRecord, DtoT extends DataTransferObject> KafkaSagaMessageHandlerBuilder addMessageType(
      final String messageType,
      final MessageToDtoMapper<MessageT, DtoT> mapper,
      final Consumer<DtoT> listener) {
    final MessageConsumer<MessageT, DtoT> consumer = new MessageConsumer<>(mapper, listener);
    consumers.put(messageType, consumer);
    return this;
  }

  public KafkaSagaMessageHandler build() {
    return new KafkaSagaMessageHandler(unmodifiableMap(consumers));
  }
}

