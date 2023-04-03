package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.event.EmptyEvent;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
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
public class OrderApprovalSaga implements SagaStep<RestaurantApprovalResponseDto, EmptyEvent, OrderCancelledEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  @Transactional
  public EmptyEvent process(final RestaurantApprovalResponseDto approvalResponse) {
    final OrderId orderId = OrderId.of(approvalResponse.getOrderId());
    log.info("Approving order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    orderDomainService.approveOrder(order);
    final Order savedOrder = orderRepository.save(order);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }
    log.info("Order with id {} is approved", orderId);
    return EmptyEvent.INSTANCE;
  }

  @Override
  @Transactional
  public OrderCancelledEvent rollback(final RestaurantApprovalResponseDto approvalResponse) {
    final OrderId orderId = OrderId.of(approvalResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final ValidationResult result = approvalResponse.getResult();
    final OrderCancelledEvent orderCancelledEvent = orderDomainService.cancelOrderPayment(order, result);
    final Order savedOrder = orderRepository.save(order);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }
    log.info("Payment is cancelled for order with id {} ", orderId);
    return orderCancelledEvent;
  }
}
