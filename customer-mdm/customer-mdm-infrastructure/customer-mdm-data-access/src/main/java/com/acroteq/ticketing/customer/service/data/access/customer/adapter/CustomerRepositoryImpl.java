package com.acroteq.ticketing.customer.service.data.access.customer.adapter;

import com.acroteq.ticketing.customer.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.customer.service.data.access.customer.mapper.CustomerDomainToJpaMapper;
import com.acroteq.ticketing.customer.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.customer.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.ports.output.repository.CustomerRepository;
import com.acroteq.ticketing.domain.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository customerJpaRepository;
  private final CustomerJpaToDomainMapper customerJpaToDomainMapper;
  private final CustomerDomainToJpaMapper customerDomainToJpaMapper;

  @Override
  public Customer save(final Customer customer) {
    final CustomerJpaEntity customerJpaEntity = customerDomainToJpaMapper.convertDomainToJpa(customer);
    final CustomerJpaEntity savedEntity = customerJpaRepository.save(customerJpaEntity);
    return customerJpaToDomainMapper.convertJpaToDomain(savedEntity);
  }

  @Override
  public Optional<Customer> findById(final CustomerId customerId) {
    return customerJpaRepository.findById(customerId.getValue())
                                .map(customerJpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public void deleteById(final CustomerId customerId) {
    customerJpaRepository.deleteById(customerId.getValue());
  }
}
