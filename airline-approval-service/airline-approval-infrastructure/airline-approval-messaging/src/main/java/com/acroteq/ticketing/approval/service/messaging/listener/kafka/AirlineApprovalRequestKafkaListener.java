package com.acroteq.ticketing.approval.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.order.OrderApprovalRequestMessageListener;
import com.acroteq.ticketing.approval.service.messaging.mapper.approval.AirlineApprovalRequestMessageToDtoMapper;
import com.acroteq.ticketing.kafka.consumer.service.KafkaSagaMessageHandler;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Component
public class AirlineApprovalRequestKafkaListener {

  private final KafkaSagaMessageHandler messageHandler;

  public AirlineApprovalRequestKafkaListener(final OrderApprovalRequestMessageListener listener,
                                             final AirlineApprovalRequestMessageToDtoMapper requestMapper) {
    messageHandler = KafkaSagaMessageHandler.builder()
                                            .addMessageType(AirlineApprovalRequestMessage.SCHEMA$.getName(),
                                                        requestMapper,
                                                        listener::checkOrder)
                                            .build();
  }

  @KafkaListener(id = "${airline-approval-service.airline-approval.consumer-group-id}",
                 topics = "${airline-approval-service.airline-approval.request-topic-name}")
  public void receive(@Payload @Validated final List<AirlineApprovalRequestMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}"
             + ", sending for airline approval",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
