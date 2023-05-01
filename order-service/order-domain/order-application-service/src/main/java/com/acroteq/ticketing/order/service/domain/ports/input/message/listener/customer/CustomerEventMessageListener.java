package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer;

import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerCreatedDto;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDeletedDto;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerUpdatedDto;

public interface CustomerEventMessageListener {

  void customerCreated(CustomerCreatedDto customerCreatedDto);

  void customerUpdated(CustomerUpdatedDto customerUpdatedDto);

  void customerDeleted(CustomerDeletedDto customerUpdatedDto);
}
