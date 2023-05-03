package com.acroteq.ticketing.payment.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerEventDto;

public interface CustomerEventMessageListener {

  void customerCreatedOrUpdated(CustomerEventDto dto);

  void customerDeleted(Long id);
}
