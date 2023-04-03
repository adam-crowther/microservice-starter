package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.exception.CustomerNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.exception.OrderSaveFailedException;
import com.acroteq.food.ordering.system.order.service.domain.mapper.CreateOrderCommandDtoToDomainMapper;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.resolver.RestaurantResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.acroteq.food.ordering.system.precondition.Precondition.checkPrecondition;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderCreateHelper {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final RestaurantResolver restaurantResolver;
  private final CreateOrderCommandDtoToDomainMapper orderMapper;

  @Transactional
  public OrderCreatedEvent persistOrder(final CreateOrderCommandDto createOrderCommand) {
    final UUID customerId = createOrderCommand.getCustomerId();
    checkCustomer(customerId);

    final UUID restaurantId = createOrderCommand.getRestaurantId();
    final Restaurant restaurant = restaurantResolver.resolve(restaurantId);
    final Order order = orderMapper.convertDtoToDomain(createOrderCommand);

    final OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
    final Order savedOrder = orderRepository.save(order);
    if (savedOrder == null) {
      throw new OrderSaveFailedException(order.getId());
    }

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
