package com.acroteq.ticketing.order.service.data.access.order.adapter;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.mapper.OrderDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.mapper.OrderJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.order.repository.OrderJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderDomainToJpaMapper orderDomainToJpaMapper;
  private final OrderJpaToDomainMapper orderJpaToDomainMapper;

  @Override
  public Order save(final Order order) {
    final OrderJpaEntity entity = orderDomainToJpaMapper.convertDomainToEntity(order);
    final OrderJpaEntity saved = orderJpaRepository.save(entity);
    return orderJpaToDomainMapper.convertEntityToDomain(saved);
  }

  @Override
  public Optional<Order> findById(final OrderId orderId) {
    final Long id = orderId.getValue();
    return orderJpaRepository.findById(id)
                             .map(orderJpaToDomainMapper::convertEntityToDomain);
  }

  @Override
  public Optional<Order> findByTrackingId(final TrackingId trackingId) {
    final UUID id = trackingId.getValue();
    return orderJpaRepository.findByTrackingId(id)
                             .map(orderJpaToDomainMapper::convertEntityToDomain);
  }
}
