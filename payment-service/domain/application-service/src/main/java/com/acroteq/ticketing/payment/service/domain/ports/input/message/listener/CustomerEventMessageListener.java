package com.acroteq.ticketing.payment.service.domain.ports.input.message.listener;

import com.acroteq.ticketing.payment.service.domain.entity.Customer;

public interface CustomerEventMessageListener {

  void customerCreatedOrUpdated(Customer customer);

  void customerDeleted(String key);
}
