package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.food.ordering.system.order.service.domain.mapper.TrackOrderResponseDomainToDtoMapper;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.OrderRepository;
import com.acroteq.food.ordering.system.order.service.domain.valueobject.TrackingId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
class OrderTrackCommandHandler {

  private final OrderRepository orderRepository;
  private final TrackOrderResponseDomainToDtoMapper trackOrderResponseMapper;

  @Transactional(readOnly = true)
  public TrackOrderResponseDto trackOrder(TrackOrderQueryDto trackOrderQuery) {
    final TrackingId trackingId = TrackingId.of(trackOrderQuery.getTrackingId());
    final Order order =
        orderRepository.findByTrackingId(trackingId)
                       .orElseThrow(() -> new OrderNotFoundException(trackingId));

    return trackOrderResponseMapper.convertDomainToDto(order);
  }
}
