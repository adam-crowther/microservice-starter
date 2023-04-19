package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus.COMPLETED;
import static com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus.FAILED;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.ticketing.kafka.order.avro.model.PaymentStatus;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentResponseDto;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.PaymentResponseMessageToDtoMapper;
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
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseMessage> {

  private final Map<PaymentStatus, Consumer<PaymentResponseMessage>> messageHandlers = Map.of(COMPLETED,
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
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} payment responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final PaymentResponseMessage message) {
    final Consumer<PaymentResponseMessage> messageHandler = Optional.of(message)
                                                                    .map(PaymentResponseMessage::getPaymentStatus)
                                                                    .map(messageHandlers::get)
                                                                    .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final PaymentResponseMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final PaymentResponseMessage message) {
    final PaymentStatus orderStatus = message.getPaymentStatus();
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
