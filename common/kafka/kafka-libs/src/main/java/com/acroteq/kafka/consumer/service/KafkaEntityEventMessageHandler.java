package com.acroteq.kafka.consumer.service;

import static com.acroteq.precondition.Precondition.checkPrecondition;

import com.acroteq.application.mapper.MessageToDomainMapper;
import com.acroteq.domain.entity.ReplicatedEntity;
import com.acroteq.domain.valueobject.EntityId;
import com.acroteq.domain.valueobject.EventId;
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
  private final MessageToDomainMapper<SpecificRecord, ReplicatedEntity<? extends EntityId>> mapper;
  private final Consumer<ReplicatedEntity<? extends EntityId>> createOrUpdateConsumer;
  private final Consumer<String> deleteConsumer;

  @SuppressWarnings("unchecked")
  public <EntityT extends ReplicatedEntity<? extends EntityId>> KafkaEntityEventMessageHandler(
      final String messageType, final MessageToDomainMapper<? extends SpecificRecord, EntityT> mapper,
      final Consumer<EntityT> createOrUpdateConsumer, final Consumer<String> deleteConsumer) {
    super();
    this.messageType = messageType;
    this.mapper = (MessageToDomainMapper<SpecificRecord, ReplicatedEntity<? extends EntityId>>) mapper;
    this.createOrUpdateConsumer = (Consumer<ReplicatedEntity<? extends EntityId>>) createOrUpdateConsumer;
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
  void consumeMessage(
      final SpecificRecord message, @NonNull final String key, @NonNull final Integer partition,
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
    final EventId eventId = EventId.builder()
                                   .partition(partition)
                                   .offset(offset)
                                   .build();
    final ReplicatedEntity<? extends EntityId> entity = mapper.convert(message, eventId);
    createOrUpdateConsumer.accept(entity);
  }

  private void deleteEntity(final String key) {
    deleteConsumer.accept(key);
  }
}
