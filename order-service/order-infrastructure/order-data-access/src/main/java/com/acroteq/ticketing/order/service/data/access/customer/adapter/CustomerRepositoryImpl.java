package com.acroteq.ticketing.order.service.data.access.customer.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.mapper.CustomerDomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
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
  public Optional<Customer> findCustomer(final CustomerId customerId) {
    final Long id = customerId.getValue();
    return customerJpaRepository.findById(id)
                                .map(customerJpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public boolean customerExists(final CustomerId customerId) {
    final Long id = customerId.getValue();
    return customerJpaRepository.existsById(id);
  }

  @Override
  public Customer saveCustomer(final Customer customer) {
    final CustomerJpaEntity customerJpaEntity = customerDomainToJpaMapper.convertDomainToJpa(customer);
    final CustomerJpaEntity savedCustomerJpaEntity = customerJpaRepository.save(customerJpaEntity);
    return customerJpaToDomainMapper.convertJpaToDomain(savedCustomerJpaEntity);
  }

  @Override
  public void deleteCustomer(final CustomerId customerId) {
    customerJpaRepository.deleteById(customerId.getValue());
  }
}
