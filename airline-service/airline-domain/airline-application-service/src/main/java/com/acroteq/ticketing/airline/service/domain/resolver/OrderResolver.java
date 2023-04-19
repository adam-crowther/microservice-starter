package com.acroteq.ticketing.airline.service.domain.resolver;

import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.airline.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.airline.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.application.resolver.Resolver;
import com.acroteq.ticketing.domain.valueobject.OrderId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class OrderResolver implements Resolver<OrderId, Order> {

  private final OrderRepository orderRepository;

  @Override
  public Order resolve(final OrderId id) {
    return orderRepository.loadOrder(id)
                          .orElseThrow(() -> new OrderNotFoundException(id));
  }

  @Override
  public Order resolve(final Long id) {
    final OrderId orderId = OrderId.of(id);
    return resolve(orderId);
  }
}
