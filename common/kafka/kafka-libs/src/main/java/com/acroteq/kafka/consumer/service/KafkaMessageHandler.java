package com.acroteq.kafka.consumer.service;

import static com.acroteq.helper.StreamHelper.withCounter;
import static com.acroteq.precondition.Precondition.checkPrecondition;
import static lombok.AccessLevel.PACKAGE;

import com.acroteq.kafka.consumer.exception.MessageHandlerParameterCountMismatchException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.listener.BatchListenerFailedException;

import java.util.List;
import java.util.function.BiConsumer;

@Slf4j
@RequiredArgsConstructor(access = PACKAGE)
public abstract class KafkaMessageHandler {

  public void processMessages(@NonNull final List<? extends SpecificRecord> messages,
                              @NonNull final List<String> keys,
                              @NonNull final List<Integer> partitions,
                              @NonNull final List<Long> offsets) {
    log.info("Handling {} messages", messages.size());

    checkPrecondition(messages.size() == keys.size(), "keys", MessageHandlerParameterCountMismatchException::new);
    checkPrecondition(messages.size() == partitions.size(),
                      "partitions",
                      MessageHandlerParameterCountMismatchException::new);
    checkPrecondition(messages.size() == offsets.size(), "offsets", MessageHandlerParameterCountMismatchException::new);

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
    final String key = keys.get(counter);
    final Integer partition = partitions.get(counter);
    final Long offset = offsets.get(counter);
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

  abstract String getMessageType(SpecificRecord message);

  abstract void consumeMessage(SpecificRecord message, String key, @NonNull Integer partition, @NonNull Long offset);
}
