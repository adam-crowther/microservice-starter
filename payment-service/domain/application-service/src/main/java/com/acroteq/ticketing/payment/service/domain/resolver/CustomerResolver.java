package com.acroteq.ticketing.payment.service.domain.resolver;

import com.acroteq.application.resolver.Resolver;
import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.exception.CustomerNotFoundException;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerResolver implements Resolver<CustomerId, Customer> {

  private final CustomerRepository customerRepository;

  @Override
  public Customer resolve(final CustomerId id) {
    return customerRepository.findById(id)
                             .orElseThrow(() -> new CustomerNotFoundException(id));
  }
}
