package com.acroteq.food.ordering.system.customer.service.domain;

import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerCreatedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerDeletedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdateFailedEvent;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerUpdatedEvent;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {

  public CustomerEvent validateAndCreateCustomer(final Customer customer) {
    final ValidationResult result = customer.validateCustomer();
    customer.initializeCustomer();

    if (result.isPass()) {
      log.info("Customer with id {} is initiated", customer.getId());
      return CustomerCreatedEvent.builder()
                                 .customer(customer)
                                 .build();
    } else {
      log.info("Customer create failed for id {}", customer.getId());
      return CustomerCreateFailedEvent.builder()
                                      .customer(customer)
                                      .build();    }
  }

  public CustomerEvent validateAndUpdateCustomer(final Customer customer) {
    final ValidationResult result = customer.validateCustomer();

    if (result.isPass()) {
      log.info("Customer with id {} is initiated", customer.getId());
      return CustomerUpdatedEvent.builder()
                                 .customer(customer)
                                 .build();
    } else {
      log.info("Customer initiation failed for order id: {}", customer.getId());
      return CustomerUpdateFailedEvent.builder()
                                      .customer(customer)
                                      .build();
    }
  }

  public CustomerEvent deleteCustomer(final CustomerId customerId) {

      log.info("Customer with id {} is deleted", customerId);
      return CustomerDeletedEvent.builder()
                                 .customerId(customerId)
                                 .build();
  }
}
