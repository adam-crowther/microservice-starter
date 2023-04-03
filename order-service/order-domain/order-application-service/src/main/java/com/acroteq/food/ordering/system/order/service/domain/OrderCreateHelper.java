package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.exception.CustomerNotFoundException;
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
public class OrderCreateHelper {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantResolver restaurantResolver;
  private final OrderMapper orderMapper;

  @Transactional
  public OrderCreatedEvent persistOrder(final CreateOrderCommand createOrderCommand) {
    final UUID customerId = createOrderCommand.getCustomerId();
    checkCustomer(customerId);

    final UUID restaurantId = createOrderCommand.getRestaurantId();
    final Restaurant restaurant = restaurantResolver.resolve(restaurantId);
    final Order order = orderMapper.convert(createOrderCommand);

    final OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
    orderRepository.save(order);

    log.info("Created order {}", order.getId());
    return orderCreatedEvent;
  }

  private void checkCustomer(final UUID id) {
    final CustomerId customerId = CustomerId.of(id);
    checkPrecondition(customerRepository.customerExists(customerId),
                      CustomerNotFoundException::new,
                      customerId);
  }
}
