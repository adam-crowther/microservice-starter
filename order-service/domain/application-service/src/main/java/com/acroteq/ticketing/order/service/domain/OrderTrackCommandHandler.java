package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderTrackCommandHandler {

  private final OrderRepository orderRepository;

  @Transactional(readOnly = true)
  public Optional<Order> trackOrder(final TrackingId trackingId) {
    return orderRepository.findByTrackingId(trackingId);
  }
}
