package com.acroteq.ticketing.order.service.domain.ports.output.repository;

import com.acroteq.application.repository.ReadRepository;
import com.acroteq.application.repository.WriteRepository;
import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository extends ReadRepository<OrderId, Order>, WriteRepository<OrderId, Order> {

  Optional<Order> findByTrackingId(TrackingId trackingId);
}
