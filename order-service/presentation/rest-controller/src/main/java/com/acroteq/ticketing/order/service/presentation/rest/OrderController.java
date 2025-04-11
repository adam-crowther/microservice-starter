package com.acroteq.ticketing.order.service.presentation.rest;

import com.acroteq.ticketing.order.service.domain.exception.OrderNotFoundException;
import com.acroteq.ticketing.order.service.domain.ports.input.service.OrderApplicationService;
import com.acroteq.ticketing.order.service.domain.valueobject.TrackingId;
import com.acroteq.ticketing.order.service.presentation.api.OrdersApi;
import com.acroteq.ticketing.order.service.presentation.mapper.CreateOrderResponseMapper;
import com.acroteq.ticketing.order.service.presentation.mapper.OrderMapper;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrder;
import com.acroteq.ticketing.order.service.presentation.model.CreateOrderResponse;
import com.acroteq.ticketing.order.service.presentation.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * The REST controller class is for conversion from the API model to the application model, and to bridge the REST API
 * implementation to the service-application layer. The controller should NOT contain business logic.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "${order-service.permit-cross-origin-from}")
public class OrderController implements OrdersApi {

  private final OrderMapper orderMapper;
  private final CreateOrderResponseMapper responseMapper;
  private final OrderApplicationService orderApplicationService;

  @Override
  public ResponseEntity<CreateOrderResponse> createOrder(final CreateOrder createOrder) {
    log.info("Creating order for customer {} at airline {}", createOrder.getCustomerId(), createOrder.getAirlineId());

    final com.acroteq.ticketing.order.service.domain.entity.Order order = orderMapper.convert(createOrder);
    final com.acroteq.ticketing.order.service.domain.entity.Order savedOrder =
        orderApplicationService.createOrder(order);
    final CreateOrderResponse response = responseMapper.convert(savedOrder);

    log.info("Order created with tracking id {}", savedOrder.getTrackingId());
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<Order> getOrderByTrackingId(final UUID trackingId) {
    final TrackingId id = TrackingId.of(trackingId);
    final Order order = orderApplicationService.trackOrder(id)
                                               .map(orderMapper::convert)
                                               .orElseThrow(() -> new OrderNotFoundException(id));
    return ResponseEntity.ok(order);
  }
}
