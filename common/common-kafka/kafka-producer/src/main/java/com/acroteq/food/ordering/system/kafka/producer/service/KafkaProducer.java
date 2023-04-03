package com.acroteq.food.ordering.system.kafka.producer.service;

import com.acroteq.food.ordering.system.domain.valueobject.BaseId;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;

import java.util.function.BiFunction;

public interface KafkaProducer<I extends BaseId<?>, V extends SpecificRecordBase> {

  void send(String topicName, I id, V value, BiFunction<SendResult<String, V>, Throwable, SendResult<String, V>> callback);
}
