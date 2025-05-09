package com.acroteq.ticketing.order.service.data.access.order.adapter;

import com.acroteq.domain.valueobject.OrderId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.mapper.OrderJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.repository.OrderJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Order;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryImpl extends ReadWriteRepositoryImpl<OrderId, Order, OrderJpaEntity>
    implements OrderRepository {

  private final OrderJpaRepository jpaRepository;
  private final OrderJpaMapper jpaMapper;

  public OrderRepositoryImpl(final OrderJpaRepository jpaRepository, final OrderJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public Optional<Order> findByTrackingId(final TrackingId trackingId) {
    final String id = trackingId.getValue()
                                .toString();
    return jpaRepository.findByTrackingId(id)
                        .map(jpaMapper::convert);
  }
}
