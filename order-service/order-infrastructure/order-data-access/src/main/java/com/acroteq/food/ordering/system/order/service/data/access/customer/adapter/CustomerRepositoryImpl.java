package com.acroteq.food.ordering.system.order.service.data.access.customer.adapter;

import com.acroteq.food.ordering.system.domain.valueobject.CustomerId;
import com.acroteq.food.ordering.system.order.service.data.access.customer.entity.CustomerEntity;
import com.acroteq.food.ordering.system.order.service.data.access.customer.mapper.CustomerDomainToEntityMapper;
import com.acroteq.food.ordering.system.order.service.data.access.customer.mapper.CustomerEntityToDomainMapper;
import com.acroteq.food.ordering.system.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;
import com.acroteq.food.ordering.system.order.service.domain.ports.output.repository.CustomerRepository;
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
  public Optional<Customer> findCustomer(final CustomerId customerId) {
    final UUID id = customerId.getValue();
    return customerJpaRepository.findById(id)
                                .map(customerEntityToDomainMapper::convertEntityToDomain);
  }

  @Override
  public boolean customerExists(final CustomerId customerId) {
    final UUID id = customerId.getValue();
    return customerJpaRepository.existsById(id);
  }

  @Override
  public Customer saveCustomer(final Customer customer) {
    final CustomerEntity customerEntity = customerDomainToEntityMapper.convertDomainToEntity(customer);
    final CustomerEntity savedCustomerEntity = customerJpaRepository.save(customerEntity);
    return customerEntityToDomainMapper.convertEntityToDomain(savedCustomerEntity);
  }

  @Override
  public void deleteCustomer(final CustomerId customerId) {
    customerJpaRepository.deleteById(customerId.getValue());
  }
}
