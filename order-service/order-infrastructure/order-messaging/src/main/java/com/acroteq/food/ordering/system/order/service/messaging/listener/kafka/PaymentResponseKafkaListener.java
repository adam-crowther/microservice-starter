package com.acroteq.food.ordering.system.order.service.messaging.listener.kafka;

import com.acroteq.food.ordering.system.kafka.consumer.service.KafkaConsumer;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.acroteq.food.ordering.system.order.service.messaging.mapper.PaymentResponseMessageToDtoMapper;
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

import static com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus.CANCELLED;
import static com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus.COMPLETED;
import static com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus.FAILED;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseMessage> {

  private final Map<PaymentOrderStatus, Consumer<PaymentResponseMessage>> MESSAGE_HANDLERS = Map.of(COMPLETED,
                                                                                                    this::paymentCompleteMessageHandler,
                                                                                                    CANCELLED,
                                                                                                    this::paymentCancelledMessageHandler,
                                                                                                    FAILED,
                                                                                                    this::paymentCancelledMessageHandler);

  private final PaymentResponseMessageListener messageListener;
  private final PaymentResponseMessageToDtoMapper mapper;


  @Override
  @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
                 topics = "${order-service.payment-response-topic-name}")
  public void receive(@Payload final List<PaymentResponseMessage> messages,
                      @Header(KafkaHeaders.RECEIVED_KEY) final List<String> keys,
                      @Header(KafkaHeaders.RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
    log.info("{} payment responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final PaymentResponseMessage message) {
    final Consumer<PaymentResponseMessage> messageHandler = Optional.of(message)
                                                                    .map(PaymentResponseMessage::getPaymentOrderStatus)
                                                                    .map(MESSAGE_HANDLERS::get)
                                                                    .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final PaymentResponseMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final PaymentResponseMessage message) {
    final PaymentOrderStatus orderStatus = message.getPaymentOrderStatus();
    return new UnsupportedOperationException("Unsupported order status " + orderStatus);
  }

  private void paymentCompleteMessageHandler(final PaymentResponseMessage message) {
    log.info("Processing successful payment for order id {}", message.getOrderId());
    final PaymentResponseDto paymentResponse = mapper.convertMessageToDto(message);
    messageListener.paymentCompleted(paymentResponse);
  }

  private void paymentCancelledMessageHandler(final PaymentResponseMessage message) {
    log.info("Processing unsuccessful payment for order id {}", message.getOrderId());
    final PaymentResponseDto paymentResponse = mapper.convertMessageToDto(message);
    messageListener.paymentCancelled(paymentResponse);
  }
}
