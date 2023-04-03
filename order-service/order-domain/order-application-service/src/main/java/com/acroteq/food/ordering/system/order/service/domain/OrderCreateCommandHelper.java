package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.exception.CustomerNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.RestaurantNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.mapper.CreateOrderResponseMapper;
import com.acroteq.food.ordering.system.order.service.domain.mapper.OrderMapper;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.resolver.RestaurantResolver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.acroteq.food.ordering.system.precondition.Precondition.checkPrecondition;

@Slf4j
@AllArgsConstructor
@Component
class OrderCreateCommandHelper {

  private final OrderCreateHelper orderCreateHelper;
  private final CreateOrderResponseMapper createOrderResponseMapper;
  private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

  public CreateOrderResponse createOrder(final CreateOrderCommand createOrderCommand) {
    final OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);

    final Order order = orderCreatedEvent.getOrder();
    log.info("Created order {}", order.getId());

    orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    return createOrderResponseMapper.convert(order);
  }
}
