package com.acroteq.food.ordering.system.order.service.data.access.order.adapter;

import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.order.service.data.access.order.entity.OrderEntity;
import com.acroteq.food.ordering.system.order.service.data.access.order.mapper.OrderDomainToEntityMapper;
import com.acroteq.food.ordering.system.order.service.data.access.order.mapper.OrderEntityToDomainMapper;
import com.acroteq.food.ordering.system.order.service.data.access.order.repository.OrderJpaRepository;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderDomainToEntityMapper orderDomainToEntityMapper;
  private final OrderEntityToDomainMapper orderEntityToDomainMapper;

  @Override
  public Order save(final Order order) {
    final OrderEntity entity = orderDomainToEntityMapper.convertDomainToEntity(order);
    final OrderEntity saved = orderJpaRepository.save(entity);
    return orderEntityToDomainMapper.convertEntityToDomain(saved);
  }

  @Override
  public Optional<Order> findById(final OrderId orderId) {
    final UUID id = orderId.getValue();
    return orderJpaRepository.findById(id)
                             .map(orderEntityToDomainMapper::convertEntityToDomain);
  }

  @Override
  public Optional<Order> findByTrackingId(final TrackingId trackingId) {
    final UUID id = trackingId.getValue();
    return orderJpaRepository.findByTrackingId(id)
                             .map(orderEntityToDomainMapper::convertEntityToDomain);
  }
}
