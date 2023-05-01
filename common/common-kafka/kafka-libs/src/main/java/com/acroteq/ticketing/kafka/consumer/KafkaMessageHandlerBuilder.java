package com.acroteq.ticketing.kafka.consumer;


import static java.util.Collections.unmodifiableMap;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class KafkaMessageHandlerBuilder {

  private final Map<String, MessageHandler<? extends SpecificRecord, ? extends Dto>> configs = new HashMap<>();

  public <MessageT extends SpecificRecord, DtoT extends Dto> KafkaMessageHandlerBuilder addMessageType(final String messageType,
                                                                                                       final MessageToDtoMapper<MessageT, DtoT> mapper,
                                                                                                       final Consumer<DtoT> listener) {
    final MessageHandler<MessageT, DtoT> config = new MessageHandler<>(messageType, mapper, listener);
    configs.put(messageType, config);
    return this;
  }

  public KafkaMessageHandler build() {
    return new KafkaMessageHandler(unmodifiableMap(configs));
  }
}

@RequiredArgsConstructor
@Value
class MessageHandler<MessageT extends SpecificRecord, DtoT extends Dto> {

  String messageType;
  MessageToDtoMapper<MessageT, DtoT> mapper;
  Consumer<DtoT> listener;

  void handleEvent(final MessageT message) {
    final DtoT dto = mapper.convertMessageToDto(message);
    listener.accept(dto);
  }
}



