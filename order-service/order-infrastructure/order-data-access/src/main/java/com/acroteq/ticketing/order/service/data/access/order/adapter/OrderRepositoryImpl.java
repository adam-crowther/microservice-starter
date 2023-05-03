package com.acroteq.ticketing.order.service.data.access.order.adapter;

import com.acroteq.ticketing.domain.valueobject.OrderId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.order.entity.OrderJpaEntity;
import com.acroteq.ticketing.order.service.data.access.order.mapper.OrderDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.order.mapper.OrderJpaToDomainMapper;
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
  private final OrderJpaToDomainMapper jpaToDomainMapper;

  public OrderRepositoryImpl(final OrderJpaRepository jpaRepository,
                             final OrderJpaToDomainMapper jpaToDomainMapper,
                             final OrderDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaToDomainMapper = jpaToDomainMapper;
  }

  @Override
  public Optional<Order> findByTrackingId(final TrackingId trackingId) {
    final String id = trackingId.getValue()
                                .toString();
    return jpaRepository.findByTrackingId(id)
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }
}
