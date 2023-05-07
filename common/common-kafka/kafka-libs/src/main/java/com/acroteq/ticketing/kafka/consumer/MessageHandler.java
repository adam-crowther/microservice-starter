package com.acroteq.ticketing.kafka.consumer;

import static com.acroteq.ticketing.helper.StreamHelper.withCounter;
import static lombok.AccessLevel.PACKAGE;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.listener.BatchListenerFailedException;

import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public abstract class MessageHandler {

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

  // This is OK in a message listener.  We have to catch, log and rethrow everything, that's the point.
  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  private void processMessage(final int counter,
                              final SpecificRecord message,
                              final List<String> keys,
                              final List<Integer> partitions,
                              final List<Long> offsets) {
    final String key = getParameterSafely(keys, counter);
    final Integer partition = getParameterSafely(partitions, counter);
    final Long offset = getParameterSafely(offsets, counter);
    final String messageType = getMessageType(message);

    try {
      log.info("Invoking listener for message {} with key {}", messageType, key);
      consumeMessage(message, key, partition, offset);
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
      log.error(exceptionMessage, exception);
      throw new BatchListenerFailedException(exceptionMessage, exception, counter);
    }
  }

  @Nullable
  private <TypeT> TypeT getParameterSafely(final List<TypeT> parameters, final int counter) {
    final TypeT key;
    if (counter < parameters.size()) {
      key = parameters.get(counter);
    } else {
      key = null;
    }
    return key;
  }

  abstract String getMessageType(SpecificRecord message);

  abstract void consumeMessage(SpecificRecord message, String key, Integer partition, Long offset);
}
