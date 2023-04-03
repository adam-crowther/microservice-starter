package com.acroteq.food.ordering.system.order.service.domain;

import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.order.service.domain.entity.Order;
import com.acroteq.food.ordering.system.order.service.domain.entity.Restaurant;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderCreatedEvent;
import com.acroteq.food.ordering.system.order.service.domain.event.OrderPaidEvent;

public interface OrderDomainService {

  OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);

  OrderPaidEvent payOrder(Order order);

  void approveOrder(Order order);

  OrderCancelledEvent cancelOrderPayment(Order order, ValidationResult cancelResult);

  void cancelOrder(Order order, ValidationResult cancelResult);
}
