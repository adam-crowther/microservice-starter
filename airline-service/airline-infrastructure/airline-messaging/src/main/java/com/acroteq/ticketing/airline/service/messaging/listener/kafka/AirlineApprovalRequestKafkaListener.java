package com.acroteq.ticketing.airline.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.PARTITION;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;

import com.acroteq.ticketing.airline.service.domain.dto.AirlineApprovalRequestDto;
import com.acroteq.ticketing.airline.service.domain.ports.input.message.listener.AirlineApprovalRequestMessageListener;
import com.acroteq.ticketing.airline.service.messaging.mapper.AirlineApprovalRequestMessageApiToDtoMapper;
import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.order.avro.model.AirlineApprovalRequestMessage;
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

  private final AirlineApprovalRequestMessageListener messageListener;
  private final AirlineApprovalRequestMessageApiToDtoMapper mapper;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.airline-approval-consumer-group-id}",
                 topics = "${airline-service.airline-approval-request-topic-name}")
  public void receive(@Payload final List<AirlineApprovalRequestMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(PARTITION) final List<Integer> partitions,
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
      messageListener.approveOrder(requestDto);
    });
  }
}
