package com.acroteq.food.ordering.system.order.service.messaging.listener.kafka;

import com.acroteq.food.ordering.system.kafka.consumer.service.KafkaConsumer;
import com.acroteq.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus;
import com.acroteq.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseMessage;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import com.acroteq.food.ordering.system.order.service.messaging.mapper.RestaurantApprovalResponseMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.acroteq.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus.APPROVED;
import static com.acroteq.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus.REJECTED;

@Slf4j
@RequiredArgsConstructor
@Component
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseMessage> {

  private final Map<OrderApprovalStatus, Consumer<RestaurantApprovalResponseMessage>> MESSAGE_HANDLERS =
      Map.of(APPROVED,
             this::orderApprovedMessageHandler,
             REJECTED,
             this::orderRejectedMessageHandler);
  private final RestaurantApprovalResponseMessageListener messageListener;
  private final RestaurantApprovalResponseMessageMapper mapper;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
                 topics = "${order-service.restaurant-approval-response-topic-name}")
  public void receive(@Payload final List<RestaurantApprovalResponseMessage> messages,
                      @Header(KafkaHeaders.RECEIVED_KEY) final List<String> keys,
                      @Header(KafkaHeaders.RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
    log.info("{} restaurantApproval responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }


  private void handleMessage(final RestaurantApprovalResponseMessage message) {
    final Consumer<RestaurantApprovalResponseMessage> messageHandler = Optional.of(message)
                                                                               .map(RestaurantApprovalResponseMessage::getOrderApprovalStatus)
                                                                               .map(MESSAGE_HANDLERS::get)
                                                                               .orElseThrow(
                                                                                   unsupportedOperationException(message));

    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final RestaurantApprovalResponseMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final RestaurantApprovalResponseMessage message) {
    final OrderApprovalStatus orderStatus = message.getOrderApprovalStatus();
    return new UnsupportedOperationException("Unsupported order status " + orderStatus);
  }

  private void orderApprovedMessageHandler(final RestaurantApprovalResponseMessage message) {
    log.info("Processing approved order for order id {}", message.getOrderId());
    final RestaurantApprovalResponseDto restaurantApprovalResponse = mapper.convertMessageToDto(message);
    messageListener.orderApproved(restaurantApprovalResponse);
  }

  private void orderRejectedMessageHandler(final RestaurantApprovalResponseMessage message) {
    log.info("Processing rejected order for order id {}", message.getOrderId());
    final RestaurantApprovalResponseDto restaurantApprovalResponse = mapper.convertMessageToDto(message);
    messageListener.orderRejected(restaurantApprovalResponse);
  }
}
