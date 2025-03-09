package com.acroteq.ticketing.order.service.domain.ports.input.service;

import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderQueryDto;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import jakarta.validation.Valid;

public interface OrderApplicationService {

  CreateOrderResponseDto createOrder(@Valid CreateOrderCommandDto createOrderCommand);

  TrackOrderResponseDto trackOrder(@Valid TrackOrderQueryDto trackOrderQuery);
}
