package com.acroteq.ticketing.payment.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerCreatedDto;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDeletedDto;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerUpdatedDto;

public interface CustomerEventMessageListener {

  void customerCreated(CustomerCreatedDto dto);

  void customerUpdated(CustomerUpdatedDto dto);

  void customerDeleted(CustomerDeletedDto dto);
}
