package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@RequiredArgsConstructor
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

  private final OrderCreateCommandHandler orderCreateCommandHandler;
  private final OrderTrackCommandHandler orderTrackCommandHandler;

  @Override
  public CreateOrderResponseDto createOrder(final CreateOrderCommandDto createOrderCommand) {
    return orderCreateCommandHandler.createOrder(createOrderCommand);
  }

  @Override
  public TrackOrderResponseDto trackOrder(final TrackOrderQueryDto trackOrderQuery) {
    return orderTrackCommandHandler.trackOrder(trackOrderQuery);
  }
}
