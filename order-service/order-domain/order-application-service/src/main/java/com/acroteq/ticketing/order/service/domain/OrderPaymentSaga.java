package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.event.EmptyEvent;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;
import com.acroteq.ticketing.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.order.service.domain.exception.OrderSaveFailedException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPaymentSaga implements SagaStep<PaymentResponseDto, OrderPaidEvent, EmptyEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  public OrderPaidEvent process(final PaymentResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Completing payment for order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final Order paidOrder = orderDomainService.payOrder(order);
    final Order savedOrder = orderRepository.save(paidOrder);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Order with id {} is paid", orderId);
    return OrderPaidEvent.builder()
                         .order(paidOrder)
                         .build();
  }

  @Override
  public EmptyEvent rollback(final PaymentResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final Order cancelledOrder = orderDomainService.cancelOrder(order, paymentResponse.getResult());
    final Order savedOrder = orderRepository.save(cancelledOrder);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Order with id {} is cancelled", orderId);
    return EmptyEvent.INSTANCE;
  }
}
