package com.acroteq.ticketing.kafka.consumer;


import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.application.dto.DataTransferObject;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class KafkaMessageHandlerBuilder implements HandlerBuilder<KafkaMessageHandlerBuilder> {

  private final Map<String, MessageConsumer<? extends SpecificRecord, ? extends DataTransferObject>> consumers =
      new HashMap<>();

  public <MessageT extends SpecificRecord, DtoT extends DataTransferObject> KafkaMessageHandlerBuilder addMessageType(
      final String messageType,
      final MessageToDtoMapper<MessageT, DtoT> mapper,
      final Consumer<DtoT> listener) {
    final MessageConsumer<MessageT, DtoT> consumer = new MessageConsumer<>(messageType, mapper, listener);
    consumers.put(messageType, consumer);
    return this;
  }

  public KafkaMessageHandler build() {
    return new KafkaMessageHandler(unmodifiableMap(consumers));
  }

  @Override
  public KafkaMessageHandlerBuilder self() {
    return this;
  }
}


