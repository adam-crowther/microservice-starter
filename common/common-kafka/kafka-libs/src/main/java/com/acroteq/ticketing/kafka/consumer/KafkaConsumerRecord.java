package com.acroteq.ticketing.kafka.consumer;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import java.util.Optional;

@Value
@Builder
class KafkaConsumerRecord<MessageT extends SpecificRecord> {

  private static final String UNRECOGNISED_MESSAGE_TYPE = "UNRECOGNISED_MESSAGE_TYPE";

  int counter;
  String key;
  @NonNull MessageT message;
  Integer partition;
  Long offset;

  String getMessageType() {
    return Optional.of(message)
                   .map(SpecificRecord::getSchema)
                   .map(Schema::getName)
                   .orElse(UNRECOGNISED_MESSAGE_TYPE);

  }
}
