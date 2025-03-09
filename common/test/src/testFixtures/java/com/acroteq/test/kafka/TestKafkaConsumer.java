package com.acroteq.test.kafka;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG;
import static java.util.Collections.singleton;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverPartition;
import reactor.kafka.receiver.ReceiverRecord;

import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.BiConsumer;

@Slf4j
public class TestKafkaConsumer<MessageT extends SpecificRecord> {

  private final String topic;

  private final Deque<MessageT> messages = new ConcurrentLinkedDeque<>();
  private final Deque<CompletableFuture<MessageT>> pollRequests = new ConcurrentLinkedDeque<>();

  private final ReceiverOptions<String, MessageT> receiverOptions;
  private Disposable disposable;

  public TestKafkaConsumer(final String bootstrapServers, final String topic, final String group) {

    final Map<String, Object> props = Map.of(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                                             bootstrapServers,
                                             ConsumerConfig.CLIENT_ID_CONFIG,
                                             topic,
                                             ConsumerConfig.GROUP_ID_CONFIG,
                                             group,
                                             ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                                             StringDeserializer.class,
                                             ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                                             KafkaAvroDeserializer.class,
                                             ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                                             "earliest",
                                             SCHEMA_REGISTRY_URL_CONFIG,
                                             "mock://my-scope-name",
                                             SPECIFIC_AVRO_READER_CONFIG,
                                             "true");
    receiverOptions = ReceiverOptions.create(props);

    this.topic = topic;
  }

  @SneakyThrows
  public void subscribe() {

    final ReceiverOptions<String, MessageT> options = receiverOptions.subscription(singleton(topic))
                                                                     .addAssignListener(this::assignListener)
                                                                     .addRevokeListener(this::revokeListener);
    final Flux<ReceiverRecord<String, MessageT>> kafkaFlux = KafkaReceiver.create(options)
                                                                          .receive();
    disposable = kafkaFlux.subscribe(this::processNextMessage);
  }

  private void processNextMessage(final ReceiverRecord<String, MessageT> record) {
    final ReceiverOffset offset = record.receiverOffset();
    final TopicPartition topicPartition = offset.topicPartition();
    log.info("Received message: topic={}, partition={}, offset={}, key={}",
             topicPartition.topic(),
             topicPartition.partition(),
             offset.offset(),
             record.key());
    offset.acknowledge();

    final MessageT message = record.value();
    messages.addFirst(message);

    Optional.of(pollRequests)
            .map(Deque::pollLast)
            .ifPresent(future -> future.complete(message));
  }

  private void assignListener(final Collection<ReceiverPartition> partitions) {
    log.debug("onPartitionsAssigned {}", partitions);
  }

  private void revokeListener(final Collection<ReceiverPartition> partitions) {
    log.debug("onPartitionsRevoked {}", partitions);
  }

  public Mono<MessageT> poll() {
    final CompletableFuture<MessageT> future = createNewFuture();

    Optional.of(messages)
            .map(Deque::pollLast)
            .ifPresent(future::complete);

    return Mono.fromFuture(future);
  }

  private CompletableFuture<MessageT> createNewFuture() {
    final CompletableFuture<MessageT> future = new CompletableFuture<>();
    future.whenComplete(removeMessageAndFuture(future));
    pollRequests.addFirst(future);
    return future;
  }

  private BiConsumer<MessageT, Throwable> removeMessageAndFuture(final CompletableFuture<MessageT> future) {
    return (message, exception) -> removeMessageAndFuture(future, message, exception);
  }

  private void removeMessageAndFuture(final CompletableFuture<MessageT> future,
                                      final MessageT message,
                                      final Throwable exception) {
    if (message != null) {
      pollRequests.remove(future);
      messages.remove(message);
    }

    if (exception != null) {
      log.error("Error while processing message.");
    }
  }

  public void stop() {
    disposable.dispose();
  }
}
