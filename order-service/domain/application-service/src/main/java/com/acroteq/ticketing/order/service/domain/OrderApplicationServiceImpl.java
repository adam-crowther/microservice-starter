package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.ports.input.service.OrderApplicationService;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

  private final OrderCreateCommandHandler orderCreateCommandHandler;
  private final OrderTrackCommandHandler orderTrackCommandHandler;

  @Override
  public Order createOrder(final Order order) {
    return orderCreateCommandHandler.createOrder(order);
  }

  @Override
  public Optional<Order> trackOrder(final TrackingId trackingId) {
    return orderTrackCommandHandler.trackOrder(trackingId);
  }
}
