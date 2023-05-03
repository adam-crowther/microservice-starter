package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer;

import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerEventDto;

public interface CustomerEventMessageListener {

  void customerCreatedOrUpdated(CustomerEventDto customerEventDto);

  void customerDeleted(Long id);
}
