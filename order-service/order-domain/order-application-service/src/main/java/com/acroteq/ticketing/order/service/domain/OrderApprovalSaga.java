package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.event.EmptyEvent;
import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
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
public class OrderApprovalSaga implements SagaStep<AirlineApprovalResponseDto, EmptyEvent, OrderCancelledEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  public EmptyEvent process(final AirlineApprovalResponseDto approvalResponse) {
    final OrderId orderId = OrderId.of(approvalResponse.getOrderId());
    log.info("Approving order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final Order approvedOrder = orderDomainService.approveOrder(order);
    final Order savedOrder = orderRepository.save(approvedOrder);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Order with id {} is approved", orderId);
    return EmptyEvent.INSTANCE;
  }

  @Override
  public OrderCancelledEvent rollback(final AirlineApprovalResponseDto approvalResponse) {
    final OrderId orderId = OrderId.of(approvalResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final ValidationResult result = approvalResponse.getResult();

    final Order cancelledOrder = orderDomainService.cancelOrderPayment(order, result);
    final Order savedOrder = orderRepository.save(cancelledOrder);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(orderId);
    }

    log.info("Payment is cancelled for order with id {} ", orderId);
    return OrderCancelledEvent.builder()
                              .order(savedOrder)
                              .build();
  }
}
