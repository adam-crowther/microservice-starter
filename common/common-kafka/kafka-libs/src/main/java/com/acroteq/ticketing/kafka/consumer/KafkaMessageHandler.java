package com.acroteq.ticketing.kafka.consumer;


import static com.acroteq.ticketing.helper.StreamHelper.withCounter;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.ticketing.application.dto.Dto;
import com.acroteq.ticketing.kafka.consumer.exception.MessageToDtoMapperMissingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.listener.BatchListenerFailedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public class KafkaMessageHandler {

  private final Map<String, MessageHandler<? extends SpecificRecord, ? extends Dto>> handlers;

  public static KafkaMessageHandlerBuilder builder() {
    return new KafkaMessageHandlerBuilder();
  }

  public void processMessages(final List<? extends SpecificRecord> messages,
                              final List<String> keys,
                              final List<Integer> partitions,
                              final List<Long> offsets) {
    log.info("Handling {} messages", messages.size());
    messages.forEach(withCounter(processMessage(keys, partitions, offsets)));
  }

  private BiConsumer<Integer, SpecificRecord> processMessage(final List<String> keys,
                                                             final List<Integer> partitions,
                                                             final List<Long> offsets) {
    return (counter, message) -> processMessage(counter, message, keys, partitions, offsets);
  }

  private void processMessage(final int counter,
                              final SpecificRecord message,
                              final List<String> keys,
                              final List<Integer> partitions,
                              final List<Long> offsets) {
    final String key = getParameterSafely(keys, counter);
    final Integer partition = getParameterSafely(partitions, counter);
    final Long offset = getParameterSafely(offsets, counter);

    final KafkaConsumerRecord<SpecificRecord> record = KafkaConsumerRecord.builder()
                                                                          .counter(counter)
                                                                          .key(key)
                                                                          .partition(partition)
                                                                          .offset(offset)
                                                                          .message(message)
                                                                          .build();
    processMessage(record);
  }

  @Nullable
  private <TypeT> TypeT getParameterSafely(final List<TypeT> keys, final int counter) {
    final TypeT key;
    if (counter < keys.size()) {
      key = keys.get(counter);
    } else {
      key = null;
    }
    return key;
  }

  private void processMessage(final KafkaConsumerRecord<SpecificRecord> record) {

    final String messageType = record.getMessageType();
    final String key = record.getKey();
    final Long offset = record.getOffset();
    final Integer partition = record.getPartition();
    final SpecificRecord message = record.getMessage();
    final int counter = record.getCounter();

    try {
      log.info("Invoking listener for message {} with key {}", messageType, key);

      final MessageHandler<SpecificRecord, Dto> handler = getHandler(messageType);
      handler.handleEvent(message);
      log.debug("Message listener successful for message {} with key {}, offset {}, partition {}",
                messageType,
                key,
                offset,
                partition);
    } catch (final Exception exception) {
      final String exceptionMessage = String.format(
          "Exception while processing message %s with key %s, offset %s, partition %s",
          messageType,
          key,
          offset,
          partition);
      log.error(exceptionMessage);
      throw new BatchListenerFailedException(exceptionMessage, exception, counter);
    }
  }

  private MessageHandler<SpecificRecord, Dto> getHandler(final String messageType) {
    // Upcast is OK
    // noinspection unchecked
    return Optional.of(messageType)
                   .map(handlers::get)
                   .map(handler -> (MessageHandler<SpecificRecord, Dto>) handler)
                   .orElseThrow(() -> new MessageToDtoMapperMissingException(messageType));
  }
}



