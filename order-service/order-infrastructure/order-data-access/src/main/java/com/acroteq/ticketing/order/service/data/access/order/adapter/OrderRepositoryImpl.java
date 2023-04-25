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

@RequiredArgsConstructor
@Component
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderDomainToJpaMapper orderDomainToJpaMapper;
  private final OrderJpaToDomainMapper orderJpaToDomainMapper;

  @Override
  public Order save(final Order order) {
    final OrderJpaEntity entity = orderDomainToJpaMapper.convertDomainToJpa(order);
    final OrderJpaEntity saved = orderJpaRepository.save(entity);
    return orderJpaToDomainMapper.convertJpaToDomain(saved);
  }

  @Override
  public Optional<Order> findById(final OrderId orderId) {
    final Long id = orderId.getValue();
    return orderJpaRepository.findById(id)
                             .map(orderJpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public Optional<Order> findByTrackingId(final TrackingId trackingId) {
    final String id = trackingId.getValue()
                                .toString();
    return orderJpaRepository.findByTrackingId(id)
                             .map(orderJpaToDomainMapper::convertJpaToDomain);
  }
}
