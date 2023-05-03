package com.acroteq.ticketing.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiFunction;

public interface KafkaProducer<V extends SpecificRecordBase> {

  void send(String topicName,
            Long id,
            V value,
            BiFunction<SendResult<String, V>, Throwable, SendResult<String, V>> callback);
}
