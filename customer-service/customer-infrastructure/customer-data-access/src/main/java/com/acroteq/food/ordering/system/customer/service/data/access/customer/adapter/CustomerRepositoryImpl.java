package com.acroteq.food.ordering.system.customer.service.data.access.customer.adapter;

import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.customer.service.data.access.customer.entity.CustomerEntity;
import com.acroteq.food.ordering.system.customer.service.data.access.customer.mapper.CustomerDomainToEntityMapper;
import com.acroteq.food.ordering.system.customer.service.data.access.customer.mapper.CustomerEntityToDomainMapper;
import com.acroteq.food.ordering.system.customer.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

  private final CustomerJpaRepository customerJpaRepository;
  private final CustomerEntityToDomainMapper customerEntityToDomainMapper;
  private final CustomerDomainToEntityMapper customerDomainToEntityMapper;

  @Override
  public Customer save(final Customer customer) {
    final CustomerEntity customerEntity = customerDomainToEntityMapper.convertDomainToEntity(customer);
    final CustomerEntity savedEntity = customerJpaRepository.save(customerEntity);
    return customerEntityToDomainMapper.convertEntityToDomain(savedEntity);
  }

  @Override
  public Optional<Customer> findById(final UUID customerId) {
    return customerJpaRepository.findById(customerId)
                               .map(customerEntityToDomainMapper::convertEntityToDomain);
  }

  @Override
  public void deleteById(final UUID customerId) {
    customerJpaRepository.deleteById(customerId);
  }
}
