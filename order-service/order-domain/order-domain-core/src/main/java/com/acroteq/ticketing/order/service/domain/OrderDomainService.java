package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.validation.ValidationResult;
import com.acroteq.ticketing.order.service.domain.entity.Airline;
import com.acroteq.ticketing.order.service.domain.entity.Order;

public interface OrderDomainService {

  void validate(Order order, Airline airline);

  Order payOrder(Order order);

  Order approveOrder(Order order);

  Order cancelOrderPayment(Order order, ValidationResult cancelResult);

  Order cancelOrder(Order order, ValidationResult cancelResult);
}
