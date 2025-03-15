package com.acroteq.ticketing.order.service.domain;

import com.acroteq.domain.event.EmptyEvent;
import com.acroteq.domain.validation.ValidationResult;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.saga.SagaStep;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalApprovedResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalRejectedResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.ticketing.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.order.service.domain.exception.OrderSaveFailedException;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderApprovalSagaStep
    implements SagaStep<AirlineApprovalApprovedResponseDto, EmptyEvent, AirlineApprovalRejectedResponseDto,
    OrderCancelledEvent> {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  @Override
  public EmptyEvent process(final AirlineApprovalApprovedResponseDto approvalResponse) {
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
  public OrderCancelledEvent rollback(final AirlineApprovalRejectedResponseDto approvalResponse) {
    final OrderId orderId = OrderId.of(approvalResponse.getOrderId());
    log.info("Cancelling order with id {}", orderId);
    final Order order = orderRepository.findById(orderId)
                                       .orElseThrow(() -> new OrderNotFoundException(orderId));
    final ValidationResult result = approvalResponse.getResult();

    final Order cancelledOrder = orderDomainService.cancelOrderPayment(order, result);
    final Order savedOrder = orderRepository.save(cancelledOrder);

    final UUID sagaId = approvalResponse.getSagaId();
    log.info("Payment is cancelled for order with id {} ", orderId);
    return OrderCancelledEvent.builder()
                              .sagaId(sagaId)
                              .order(savedOrder)
                              .build();
  }
}
