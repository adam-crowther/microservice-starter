package com.acroteq.ticketing.test.kafka;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@SuppressFBWarnings("EI_EXPOSE_REP2")
@Slf4j
@RequiredArgsConstructor
@Component
public class TestKafkaProducer<ValueT extends SpecificRecord> {

  private final KafkaTemplate<String, ValueT> kafkaTemplate;

  public void send(final String topic, final long id, final ValueT payload) {
    final String key = Long.toString(id);
    log.info("sending key = {}, payload='{}' to topic='{}'", key, payload, topic);

    kafkaTemplate.send(topic, key, payload);
  }
}