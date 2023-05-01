package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaMessageHandler;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRejectedResponseMessage;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval.AirlineApprovalResponseMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineApprovalApprovedResponseMessageMapper;
import com.acroteq.ticketing.order.service.messaging.mapper.airline.AirlineApprovalRejectedResponseMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineApprovalResponseKafkaListener {

  private final KafkaMessageHandler kafkaMessageHandler;

  public AirlineApprovalResponseKafkaListener(final AirlineApprovalApprovedResponseMessageMapper approvedResponseMapper,
                                              final AirlineApprovalRejectedResponseMessageMapper rejectedResponseMapper,
                                              final AirlineApprovalResponseMessageListener messageListener) {

    kafkaMessageHandler = KafkaMessageHandler.builder()
                                             .addMessageType(AirlineApprovalApprovedResponseMessage.SCHEMA$.getName(),
                                                             approvedResponseMapper,
                                                             messageListener::orderApproved)
                                             .addMessageType(AirlineApprovalRejectedResponseMessage.SCHEMA$.getName(),
                                                             rejectedResponseMapper,
                                                             messageListener::orderRejected)
                                             .build();
  }

  @KafkaListener(id = "${order-service.airline-approval.consumer-group-id}",
                 topics = "${order-service.airline-approval.response-topic-name}")
  public void receive(@Payload @Validated final List<? extends SpecificRecord> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airlineApproval responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    kafkaMessageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
