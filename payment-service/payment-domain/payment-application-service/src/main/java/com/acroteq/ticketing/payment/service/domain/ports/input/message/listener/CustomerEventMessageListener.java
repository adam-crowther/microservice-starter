package com.acroteq.ticketing.payment.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDto;

public interface CustomerEventMessageListener {

  void customerCreated(CustomerDto customerCreatedDto);

  void customerUpdated(CustomerDto customerUpdatedDto);

  void customerDeleted(Long customerId);
}
