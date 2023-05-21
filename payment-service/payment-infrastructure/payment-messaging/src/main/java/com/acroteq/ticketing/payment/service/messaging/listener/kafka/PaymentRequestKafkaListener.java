package com.acroteq.ticketing.payment.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.service.KafkaSagaMessageHandler;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelRequestMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentRequestMessage;
import com.acroteq.ticketing.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.acroteq.ticketing.payment.service.messaging.mapper.payment.PaymentCancelRequestMessageToDtoMapper;
import com.acroteq.ticketing.payment.service.messaging.mapper.payment.PaymentRequestMessageToDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Component
public class PaymentRequestKafkaListener {

  private final KafkaSagaMessageHandler messageHandler;

  public PaymentRequestKafkaListener(final PaymentRequestMessageListener listener,
                                     final PaymentRequestMessageToDtoMapper requestMapper,
                                     final PaymentCancelRequestMessageToDtoMapper cancelRequestMapper) {
    messageHandler = KafkaSagaMessageHandler.builder()
                                            .addMessageType(PaymentRequestMessage.SCHEMA$.getName(),
                                                        requestMapper,
                                                        listener::completePayment)
                                            .addMessageType(PaymentCancelRequestMessage.SCHEMA$.getName(),
                                                        cancelRequestMapper,
                                                        listener::cancelPayment)
                                            .build();
  }

  @KafkaListener(id = "${payment-service.payment.consumer-group-id}",
                 topics = "${payment-service.payment.request-topic-name}")
  public void receive(@Payload @Validated final List<PaymentRequestMessage> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} number of payment requests received with keys:{}, partitions:{} and offsets: {}",
             messages.size(),
             keys.toString(),
             partitions.toString(),
             offsets.toString());

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
