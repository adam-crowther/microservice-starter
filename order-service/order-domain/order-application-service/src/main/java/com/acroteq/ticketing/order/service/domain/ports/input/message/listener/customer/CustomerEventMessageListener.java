package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer;

import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDto;

public interface CustomerEventMessageListener {

  void customerCreated(CustomerDto customerCreatedDto);

  void customerUpdated(CustomerDto customerUpdatedDto);

  void customerDeleted(Long customerId);
}
