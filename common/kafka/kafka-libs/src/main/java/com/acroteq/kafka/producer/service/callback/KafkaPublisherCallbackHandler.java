package com.acroteq.ticketing.kafka.producer.service.callback;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
public class KafkaPublisherCallbackHandler<T extends SpecificRecord> {

  public SendResult<String, T> callback(@Nullable final SendResult<String, T> sendResult,
                                        @Nullable final Throwable exception) {
    final String messageType = Optional.ofNullable(sendResult)
                                       .map(SendResult::getProducerRecord)
                                       .map(ProducerRecord::value)
                                       .map(SpecificRecord::getSchema)
                                       .map(Schema::getName)
                                       .orElse(null);
    final String key = getNullSafeValue(sendResult, SendResult::getProducerRecord, ProducerRecord::key);
    final String topic = getNullSafeValue(sendResult, SendResult::getRecordMetadata, RecordMetadata::topic);
    final Integer partition = getNullSafeValue(sendResult, SendResult::getRecordMetadata, RecordMetadata::partition);
    final Long offset = getNullSafeValue(sendResult, SendResult::getRecordMetadata, RecordMetadata::offset);

    if (exception == null) {
      log.debug("Received callback from Kafka from sending message {} with Key {}. Topic {}, Partition {}, Offset {}",
                messageType,
                key,
                topic,
                partition,
                offset);
    } else {
      log.error("Error while sending {} with key {}. Topic {}, Partition {}, Offset {}.",
                messageType,
                key,
                topic,
                partition,
                offset,
                exception);
    }

    return sendResult;
  }

  private <MessageT, OutputT, StructT> OutputT getNullSafeValue(final SendResult<String, MessageT> sendResult,
                                                                final Function<SendResult<String, MessageT>, StructT> getStruct,
                                                                final Function<StructT, OutputT> getValue) {
    return Optional.ofNullable(sendResult)
                   .map(getStruct)
                   .map(getValue)
                   .orElse(null);
  }

}
