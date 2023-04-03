package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.mapper.CreateOrderResponseDomainToDtoMapper;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateCommandHandler {

  public static final String MESSAGE_ORDER_CREATED_SUCCESSFULLY = "Order created successfully";

  private final OrderCreateHelper orderCreateHelper;
  private final CreateOrderResponseDomainToDtoMapper createOrderResponseMapper;
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public CreateOrderResponseDto createOrder(final CreateOrderCommandDto createOrderCommand) {
    final OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);

    final Order order = orderCreatedEvent.getOrder();
    log.info("Created order {}", order.getId());

    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    return createOrderResponseMapper.convertDomainToDto(order, MESSAGE_ORDER_CREATED_SUCCESSFULLY);
  }
}
