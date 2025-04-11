package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.PaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateCommandHandler {

  private final OrderCreateHelper orderCreateHelper;
  private final PaymentRequestMessagePublisher messagePublisher;

  @Transactional
  public Order createOrder(final Order order) {
    final OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(order);
    log.info("Created order {}", order.getId());

    messagePublisher.publish(orderCreatedEvent);
    return order;
  }
}
