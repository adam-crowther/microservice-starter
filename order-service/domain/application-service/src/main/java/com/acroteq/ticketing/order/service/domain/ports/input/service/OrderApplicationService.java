package com.acroteq.ticketing.order.service.domain.ports.input.service;

import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import jakarta.validation.Valid;

import java.util.Optional;

public interface OrderApplicationService {

  Order createOrder(@Valid Order order);

  Optional<Order> trackOrder(@Valid TrackingId trackingId);
}
