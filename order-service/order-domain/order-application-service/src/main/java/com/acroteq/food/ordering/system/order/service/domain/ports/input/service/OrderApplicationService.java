package com.acroteq.food.ordering.system.order.service.domain.ports.input.service;

import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderQueryDto;
import com.acroteq.food.ordering.system.order.service.domain.dto.track.TrackOrderResponseDto;
import jakarta.validation.Valid;

public interface OrderApplicationService {

  CreateOrderResponseDto createOrder(@Valid CreateOrderCommandDto createOrderCommand);

  TrackOrderResponseDto trackOrder(@Valid TrackOrderQueryDto trackOrderQuery);
}
