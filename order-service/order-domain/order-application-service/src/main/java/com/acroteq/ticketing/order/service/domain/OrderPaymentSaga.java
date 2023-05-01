package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.event.EmptyEvent;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentCancelledResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.PaymentPaidResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;
import com.acroteq.ticketing.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.saga.SagaStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderPaymentSaga
    implements SagaStep<PaymentPaidResponseDto, OrderPaidEvent, PaymentCancelledResponseDto, EmptyEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  public OrderPaidEvent process(final PaymentPaidResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Completing payment for order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final Order paidOrder = orderDomainService.payOrder(order);
    final Order savedOrder = orderRepository.save(paidOrder);

    final UUID sagaId = paymentResponse.getSagaId();
    log.info("Order with id {} is paid", orderId);
    return OrderPaidEvent.builder()
                         .sagaId(sagaId)
                         .order(savedOrder)
                         .build();
  }

  @Override
  public EmptyEvent rollback(final PaymentCancelledResponseDto paymentResponse) {
    final OrderId orderId = OrderId.of(paymentResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final Order cancelledOrder = orderDomainService.cancelOrder(order, paymentResponse.getResult());
    orderRepository.save(cancelledOrder);

    log.info("Order with id {} is cancelled", orderId);
    return EmptyEvent.INSTANCE;
  }
}
