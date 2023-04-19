package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.mapper.CreateOrderResponseDomainToDtoMapper;
import com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateCommandHandler {

  private static final String MESSAGE_ORDER_CREATED_SUCCESSFULLY = "Order created successfully";

  private final OrderCreateHelper orderCreateHelper;
  private final CreateOrderResponseDomainToDtoMapper responseMapper;
  private final OrderCreatedPaymentRequestMessagePublisher messagePublisher;

  @Transactional
  public CreateOrderResponseDto createOrder(final CreateOrderCommandDto createOrderCommand) {
    final OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);

    final Order order = orderCreatedEvent.getOrder();
    log.info("Created order {}", order.getId());

    messagePublisher.publish(orderCreatedEvent);
    return responseMapper.convertDomainToDto(order, MESSAGE_ORDER_CREATED_SUCCESSFULLY);
  }
}
