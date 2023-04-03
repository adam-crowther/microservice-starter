package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.event.EmptyEvent;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import com.acroteq.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.OrderSaveFailedException;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponseDto, OrderPaidEvent, EmptyEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  @Transactional
  public OrderPaidEvent process(final PaymentResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Completing payment for order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final OrderPaidEvent orderPaidEvent = orderDomainService.payOrder(order);
    final Order savedOrder = orderRepository.save(order);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Order with id {} is paid", orderId);
    return orderPaidEvent;
  }

  @Override
  @Transactional
  public EmptyEvent rollback(final PaymentResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    orderDomainService.cancelOrder(order, paymentResponse.getResult());
    final Order savedOrder = orderRepository.save(order);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Order with id {} is cancelled", orderId);
    return EmptyEvent.INSTANCE;
  }
}
