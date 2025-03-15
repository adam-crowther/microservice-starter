package com.acroteq.ticketing.order.service.data;

import com.acroteq.test.kafka.TestKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.awaitility.Awaitility;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public class MasterDataUploader<ValueT extends SpecificRecord> {

  private final String topic;
  private final TestKafkaProducer<ValueT> producer;
  private final JpaRepository<?, Long> repository;

  public void upload(final Long id, final ValueT value) {
    producer.send(topic, id, value);
    Awaitility.await()
              .until(() -> repository.existsById(id));
  }
}
