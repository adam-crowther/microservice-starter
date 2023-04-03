package com.acroteq.food.ordering.system.order.service.domain.ports.input.message.listener.customer;

import com.acroteq.food.ordering.system.order.service.domain.dto.customer.CustomerDto;

import java.util.UUID;

public interface CustomerEventMessageListener {

  void customerCreated(CustomerDto customerCreatedDto);

  void customerUpdated(CustomerDto customerUpdatedDto);

  void customerDeleted(UUID customerId);
}
