package com.acroteq.ticketing.approval.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.approval.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.approval.service.domain.ports.input.message.listener.order.OrderApprovalRequestMessageListener;
import com.acroteq.ticketing.approval.service.messaging.mapper.AirlineApprovalRequestMessageApiToDtoMapper;
import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineApprovalRequestKafkaListener implements KafkaConsumer<AirlineApprovalRequestMessage> {

  private final OrderApprovalRequestMessageListener messageListener;
  private final AirlineApprovalRequestMessageApiToDtoMapper mapper;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.airline-approval-consumer-group-id}",
                 topics = "${airline-approval-service.airline-approval-request-topic-name}")
  public void receive(@Payload final List<AirlineApprovalRequestMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}"
             + ", sending for airline approval",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messages.forEach(requestMessage -> {
      log.info("Processing order approval for order id: {}", requestMessage.getOrderId());
      final AirlineApprovalRequestDto requestDto = mapper.convertApiToDto(requestMessage);
      messageListener.checkOrder(requestDto);
    });
  }
}
