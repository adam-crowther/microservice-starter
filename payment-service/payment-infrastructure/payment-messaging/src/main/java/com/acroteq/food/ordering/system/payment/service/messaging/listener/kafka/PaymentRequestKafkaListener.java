package com.acroteq.food.ordering.system.payment.service.messaging.listener.kafka;

import com.acroteq.food.ordering.system.kafka.consumer.service.KafkaConsumer;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentRequestMessage;
import com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentRequestDto;
import com.acroteq.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.acroteq.food.ordering.system.payment.service.messaging.mapper.PaymentRequestMessageToDtoMapper;
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
import static com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus.PENDING;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestMessage> {

  private final PaymentRequestMessageListener paymentRequestMessageListener;
  private final PaymentRequestMessageToDtoMapper mapper;

  private final Map<PaymentOrderStatus, Consumer<PaymentRequestMessage>>
      MESSAGE_HANDLERS = Map.of(PENDING,
                                this::paymentPendingMessageHandler,
                                CANCELLED,
                                this::paymentCancelledMessageHandler);

  @Override
  @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
                 topics = "${payment-service.payment-request-topic-name}")
  public void receive(@Payload final List<PaymentRequestMessage> messages,
                      @Header(KafkaHeaders.RECEIVED_KEY) final List<String> keys,
                      @Header(KafkaHeaders.RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(KafkaHeaders.OFFSET) final List<Long> offsets) {
    log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messages.forEach(this::handleMessage);
  }

  private void handleMessage(final PaymentRequestMessage message) {
    final Consumer<PaymentRequestMessage> messageHandler = Optional.of(message)
                                                                   .map(PaymentRequestMessage::getPaymentOrderStatus)
                                                                   .map(MESSAGE_HANDLERS::get)
                                                                   .orElseThrow(unsupportedOperationException(message));
    messageHandler.accept(message);
  }

  private static Supplier<UnsupportedOperationException> unsupportedOperationException(final PaymentRequestMessage message) {
    return () -> createUnsupportedOperationException(message);
  }

  private static UnsupportedOperationException createUnsupportedOperationException(final PaymentRequestMessage message) {
    final PaymentOrderStatus orderStatus = message.getPaymentOrderStatus();
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
