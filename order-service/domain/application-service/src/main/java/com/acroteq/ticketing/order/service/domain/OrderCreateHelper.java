package com.acroteq.ticketing.order.service.domain;

import static java.util.UUID.randomUUID;

import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderCreateHelper {

  private final OrderDomainService orderDomainService;
  private final OrderRepository orderRepository;

  OrderCreatedEvent persistOrder(final Order order) {
    final Airline airline = order.getAirline();
    orderDomainService.validate(order, airline);
    final Order savedOrder = orderRepository.save(order);

    log.info("Created order {}", savedOrder.getId());
    return OrderCreatedEvent.builder()
                            .sagaId(randomUUID())
                            .order(savedOrder)
                            .build();
  }
}
