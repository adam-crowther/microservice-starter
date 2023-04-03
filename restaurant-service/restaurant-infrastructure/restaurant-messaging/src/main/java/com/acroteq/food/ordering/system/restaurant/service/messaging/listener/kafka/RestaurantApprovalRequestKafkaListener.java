package com.acroteq.food.ordering.system.restaurant.service.messaging.listener.kafka;

import com.acroteq.food.ordering.system.kafka.consumer.service.KafkaConsumer;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestMessage;
import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import com.acroteq.food.ordering.system.restaurant.service.messaging.mapper.RestaurantApprovalRequestMessageApiToDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantApprovalRequestKafkaListener implements KafkaConsumer<RestaurantApprovalRequestMessage> {

  private final RestaurantApprovalRequestMessageListener restaurantApprovalRequestMessageListener;
  private final RestaurantApprovalRequestMessageApiToDtoMapper mapper;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
                 topics = "${restaurant-service.restaurant-approval-request-topic-name}")
  public void receive(@Payload final List<RestaurantApprovalRequestMessage> messages,
                      @Header(KafkaHeaders.RECEIVED_KEY) final List<String> keys,
                      @Header(KafkaHeaders.PARTITION) final List<Integer> partitions,
                      @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
    log.info("{} number of orders approval requests received with keys {}, partitions {} and offsets {}" +
             ", sending for restaurant approval",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messages.forEach(requestMessage -> {
      log.info("Processing order approval for order id: {}", requestMessage.getOrderId());
      final RestaurantApprovalRequestDto requestDto = mapper.convertApiToDto(requestMessage);
      restaurantApprovalRequestMessageListener.approveOrder(requestDto);
    });
  }
}
