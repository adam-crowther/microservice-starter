package com.acroteq.ticketing.airline.service.domain.ports.output.repository;

import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.domain.valueobject.OrderId;

import java.util.Optional;

public interface OrderRepository {

  Optional<Order> loadOrder(OrderId order);
}
