package com.acroteq.ticketing.kafka.producer.service;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

public interface KafkaProducer<V extends SpecificRecord> {

  void send(String topicName, Long id, V value, BiConsumer<SendResult<String, V>, Throwable> callback);
}
