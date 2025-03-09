package com.acroteq.test.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TestKafkaProducer<MessageT extends SpecificRecord> {

  private final KafkaTemplate<String, MessageT> kafkaTemplate;

  public void send(final String topic, final long id, final MessageT payload) {
    final String key = Long.toString(id);
    log.info("sending key = {}, payload='{}' to topic='{}'", key, payload, topic);

    kafkaTemplate.send(topic, key, payload);
  }
}