package com.acroteq.food.ordering.system.customer.service.domain;

import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.event.CustomerEvent;
import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;

public interface CustomerDomainService {

  CustomerEvent validateAndCreateCustomer(Customer customer);

  CustomerEvent validateAndUpdateCustomer(final Customer customer);

  CustomerEvent deleteCustomer(final CustomerId customerIId);
}
