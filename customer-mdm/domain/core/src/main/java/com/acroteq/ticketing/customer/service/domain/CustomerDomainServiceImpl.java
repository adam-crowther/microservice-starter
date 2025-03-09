package com.acroteq.ticketing.customer.service.domain;

import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomerDomainServiceImpl implements CustomerDomainService {

  @Override
  public void validate(final Customer customer) {
    customer.validate();
    log.info("Customer with id {} is validated", customer.getId());
  }
}
