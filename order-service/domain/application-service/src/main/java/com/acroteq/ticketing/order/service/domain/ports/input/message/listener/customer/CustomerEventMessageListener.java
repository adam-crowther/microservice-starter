package com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer;

import com.acroteq.ticketing.order.service.domain.entity.Customer;

public interface CustomerEventMessageListener {

  void customerCreatedOrUpdated(Customer customer);

  void customerDeleted(String key);
}
