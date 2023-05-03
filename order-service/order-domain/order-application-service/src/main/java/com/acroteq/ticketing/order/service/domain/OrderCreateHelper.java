package com.acroteq.ticketing.order.service.domain;

import static com.acroteq.ticketing.precondition.Precondition.checkPrecondition;
import static java.util.UUID.randomUUID;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.order.service.domain.mapper.order.CreateOrderCommandDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.order.service.domain.resolver.AirlineResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateHelper {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;
  private final AirlineResolver airlineResolver;
  private final CreateOrderCommandDtoToDomainMapper orderMapper;

  OrderCreatedEvent persistOrder(final CreateOrderCommandDto createOrderCommand) {
    final Long customerId = createOrderCommand.getCustomerId();
    checkCustomer(customerId);

    final Long airlineId = createOrderCommand.getAirlineId();
    final Airline airline = airlineResolver.resolve(airlineId);
    final Order order = orderMapper.convertDtoToDomain(createOrderCommand);

    orderDomainService.validate(order, airline);
    final Order savedOrder = orderRepository.save(order);

    log.info("Created order {}", savedOrder.getId());
    return OrderCreatedEvent.builder()
                            .sagaId(randomUUID())
                            .order(savedOrder)
                            .build();
  }

  private void checkCustomer(final Long id) {
    final CustomerId customerId = CustomerId.of(id);
    checkPrecondition(customerRepository.existsById(customerId), CustomerNotFoundException::new, customerId);
  }
}
