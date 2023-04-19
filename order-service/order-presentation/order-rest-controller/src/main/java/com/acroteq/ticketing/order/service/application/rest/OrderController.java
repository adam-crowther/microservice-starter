package com.acroteq.ticketing.order.service.application.rest;

import com.acroteq.ticketing.order.service.application.api.OrdersApi;
import com.acroteq.ticketing.order.service.application.mapper.CreateOrderCommandApiToDtoMapper;
import com.acroteq.ticketing.order.service.application.mapper.CreateOrderResponseDtoToApiMapper;
import com.acroteq.ticketing.order.service.application.mapper.TrackOrderResponseDtoToApiMapper;
import com.acroteq.ticketing.order.service.application.model.CreateOrderCommand;
import com.acroteq.ticketing.order.service.application.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.application.model.Order;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderCommandDto;
import com.acroteq.ticketing.order.service.domain.dto.create.CreateOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderQueryDto;
import com.acroteq.ticketing.order.service.domain.dto.track.TrackOrderResponseDto;
import com.acroteq.ticketing.order.service.domain.ports.input.service.OrderApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class OrderController implements OrdersApi {

  private final CreateOrderCommandApiToDtoMapper createOrderCommandApiToDtoMapper;
  private final TrackOrderResponseDtoToApiMapper trackOrderResponseDtoToApiMapper;
  private final CreateOrderResponseDtoToApiMapper createOrderResponseDtoToApiMapper;
  private final OrderApplicationService orderApplicationService;

  @Override
  public ResponseEntity<CreateOrderResponse> createOrder(final CreateOrderCommand createOrderCommand) {
    log.info("Creating order for customer {} at airline {}",
             createOrderCommand.getCustomerId(),
             createOrderCommand.getAirlineId());

    final CreateOrderCommandDto commandDto = createOrderCommandApiToDtoMapper.convertApiToDto(createOrderCommand);
    final CreateOrderResponseDto responseDto = orderApplicationService.createOrder(commandDto);
    final CreateOrderResponse response = createOrderResponseDtoToApiMapper.convertDtoToApi(responseDto);

    log.info("Order created with tracking id {}", response.getTrackingId());
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Order> getOrderByTrackingId(final UUID trackingId) {
    final TrackOrderQueryDto queryDto = TrackOrderQueryDto.builder()
                                                          .trackingId(trackingId)
                                                          .build();
    final TrackOrderResponseDto responseDto = orderApplicationService.trackOrder(queryDto);
    final Order response = trackOrderResponseDtoToApiMapper.convertDtoToApi(responseDto);

    return ResponseEntity.ok(response);
  }
}
