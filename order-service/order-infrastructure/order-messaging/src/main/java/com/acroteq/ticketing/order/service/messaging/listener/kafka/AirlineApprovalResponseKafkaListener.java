package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static com.acroteq.ticketing.kafka.flight.approval.avro.model.OrderApprovalStatus.APPROVED;
import static com.acroteq.ticketing.kafka.flight.approval.avro.model.OrderApprovalStatus.REJECTED;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalResponseMessage;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.OrderApprovalStatus;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalResponseDto;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.airlineapproval.AirlineApprovalResponseMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.AirlineApprovalResponseMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Component
public class AirlineApprovalResponseKafkaListener implements KafkaConsumer<AirlineApprovalResponseMessage> {

  private final Map<OrderApprovalStatus, Consumer<AirlineApprovalResponseMessage>> messageHandlers = Map.of(APPROVED,
                                                                                                            this::orderApprovedMessageHandler,
                                                                                                            REJECTED,
                                                                                                            this::orderRejectedMessageHandler);
  private final AirlineApprovalResponseMessageListener messageListener;
  private final AirlineApprovalResponseMessageMapper mapper;

  @Override
  @KafkaListener(id = "${kafka-consumer-config.airline-approval-consumer-group-id}",
                 topics = "${order-service.airline-approval-response-topic-name}")
  public void receive(@Payload final List<AirlineApprovalResponseMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} airlineApproval responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }


  private void handleMessage(final AirlineApprovalResponseMessage message) {
    final Consumer<AirlineApprovalResponseMessage> messageHandler = Optional.of(message)
                                                                            .map(AirlineApprovalResponseMessage::getOrderApprovalStatus)
                                                                            .map(messageHandlers::get)
                                                                            .orElseThrow(unsupportedOperationException(
                                                                                message));

    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final AirlineApprovalResponseMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final AirlineApprovalResponseMessage message) {
    final OrderApprovalStatus orderStatus = message.getOrderApprovalStatus();
    return new UnsupportedOperationException("Unsupported order status " + orderStatus);
  }

  private void orderApprovedMessageHandler(final AirlineApprovalResponseMessage message) {
    log.info("Processing approved order for order id {}", message.getOrderId());
    final AirlineApprovalResponseDto airlineApprovalResponse = mapper.convertMessageToDto(message);
    messageListener.orderApproved(airlineApprovalResponse);
  }

  private void orderRejectedMessageHandler(final AirlineApprovalResponseMessage message) {
    log.info("Processing rejected order for order id {}", message.getOrderId());
    final AirlineApprovalResponseDto airlineApprovalResponse = mapper.convertMessageToDto(message);
    messageListener.orderRejected(airlineApprovalResponse);
  }
}
