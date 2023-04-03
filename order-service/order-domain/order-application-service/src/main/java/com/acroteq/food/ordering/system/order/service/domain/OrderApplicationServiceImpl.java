package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommand;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponse;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderQuery;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponse;
import com.acroteq.food.ordering.system.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@AllArgsConstructor
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {

  private final OrderCreateCommandHelper orderCreateCommandHelper;
  private final OrderTrackCommandHelper orderTrackCommandHelper;

  @Override
  public CreateOrderResponse createOrder(final CreateOrderCommand createOrderCommand) {
    return orderCreateCommandHelper.createOrder(createOrderCommand);
  }

  @Override
  public TrackOrderResponse trackOrder(final TrackOrderQuery trackOrderQuery) {
    return orderTrackCommandHelper.trackOrder(trackOrderQuery);
  }
}
