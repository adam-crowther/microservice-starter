package com.acroteq.ticketing.order.service.messaging.listener.kafka;

import static org.springframework.kafka.support.KafkaHeaders.OFFSET;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_KEY;
import static org.springframework.kafka.support.KafkaHeaders.RECEIVED_PARTITION;

import com.acroteq.ticketing.kafka.consumer.service.KafkaSagaMessageHandler;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentCancelledResponseMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentFailedResponseMessage;
import com.acroteq.ticketing.kafka.payment.avro.model.PaymentPaidResponseMessage;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentCancelledResponseMessageToDtoMapper;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentFailedResponseMessageToDtoMapper;
import com.acroteq.ticketing.order.service.messaging.mapper.payment.PaymentPaidResponseMessageToDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Slf4j
@Component
public class PaymentResponseKafkaListener {

  private final KafkaSagaMessageHandler messageHandler;

  public PaymentResponseKafkaListener(final PaymentPaidResponseMessageToDtoMapper paidResponseMapper,
                                      final PaymentCancelledResponseMessageToDtoMapper cancelledResponseMapper,
                                      final PaymentFailedResponseMessageToDtoMapper failedResponseMapper,
                                      final PaymentResponseMessageListener messageListener) {

    messageHandler = KafkaSagaMessageHandler.builder()
                                            .addMessageType(PaymentPaidResponseMessage.SCHEMA$.getName(),
                                                        paidResponseMapper,
                                                        messageListener::paymentCompleted)
                                            .addMessageType(PaymentCancelledResponseMessage.SCHEMA$.getName(),
                                                        cancelledResponseMapper,
                                                        messageListener::paymentCancelled)
                                            .addMessageType(PaymentFailedResponseMessage.SCHEMA$.getName(),
                                                        failedResponseMapper,
                                                        messageListener::paymentCancelled)
                                            .build();
  }

  @KafkaListener(id = "${order-service.payment.consumer-group-id}",
                 topics = "${order-service.payment.response-topic-name}")
  public void receive(@Payload @Validated final List<? extends SpecificRecord> messages,
                      @Header(RECEIVED_KEY) final List<String> keys,
                      @Header(RECEIVED_PARTITION) final List<Integer> partitions,
                      @Header(OFFSET) final List<Long> offsets) {
    log.info("{} payment responses received with keys {}, partitions {}, offsets {}",
             messages.size(),
             keys,
             partitions,
             offsets);

    messageHandler.processMessages(messages, keys, partitions, offsets);
  }
}
