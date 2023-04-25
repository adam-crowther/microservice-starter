package com.acroteq.ticketing.payment.service.messaging.listener.kafka;

import static com.acroteq.ticketing.kafka.payment.avro.model.PaymentStatus.CANCELLED;
import static com.acroteq.ticketing.kafka.payment.avro.model.PaymentStatus.PENDING;
import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.KafkaConsumer;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentStatus;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.acroteq.ticketing.payment.service.messaging.mapper.PaymentRequestMessageToDtoMapper;
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
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestMessage> {

  private final PaymentRequestMessageListener paymentRequestMessageListener;
  private final PaymentRequestMessageToDtoMapper mapper;

  private final Map<PaymentStatus, Consumer<PaymentRequestMessage>> messageHandlers //
      = Map.of(PENDING, this::paymentPendingMessageHandler, CANCELLED, this::paymentCancelledMessageHandler);

  @Override
  @KafkaListener(id = "${kafka-consumer-config.payment-request-consumer-group-id}",
                 topics = "${payment-service.payment-request-topic-name}")
  public void receive(@Payload final List<PaymentRequestMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final PaymentRequestMessage message) {
    final Consumer<PaymentRequestMessage> messageHandler = Optional.of(message)
                                                                   .map(PaymentRequestMessage::getPaymentStatus)
                                                                   .map(messageHandlers::get)
                                                                   .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final PaymentRequestMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final PaymentRequestMessage message) {
    final PaymentStatus orderStatus = message.getPaymentStatus();
    return new UnsupportedOperationException("Unsupported order status " + orderStatus);
  }

  private void paymentCancelledMessageHandler(final PaymentRequestMessage message) {
    log.info("Cancelling payment for order id: {}", message.getOrderId());
    final PaymentRequestDto dto = mapper.paymentRequestMessageToPaymentRequest(message);
    paymentRequestMessageListener.cancelPayment(dto);
  }

  private void paymentPendingMessageHandler(final PaymentRequestMessage message) {
    log.info("Processing payment for order id: {}", message.getOrderId());
    final PaymentRequestDto dto = mapper.paymentRequestMessageToPaymentRequest(message);
    paymentRequestMessageListener.completePayment(dto);
  }
}
