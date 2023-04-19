package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(OrderId orderId);

  Optional<Order> findByTrackingId(TrackingId trackingId);
}
