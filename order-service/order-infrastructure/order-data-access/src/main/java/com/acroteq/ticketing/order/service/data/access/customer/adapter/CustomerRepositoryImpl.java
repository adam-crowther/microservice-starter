package com.acroteq.ticketing.order.service.data.access.customer.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.exception.CustomerAlreadyExistsException;
import com.acroteq.ticketing.order.service.data.access.customer.exception.CustomerMissingIdException;
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

  private final CustomerJpaRepository jpaRepository;
  private final CustomerJpaToDomainMapper jpaToDomainMapper;
  private final CustomerDomainToJpaMapper domainToJpaMapper;

  @Override
  public Optional<Customer> findById(final CustomerId customerId) {
    final Long id = customerId.getValue();
    return jpaRepository.findById(id)
                        .map(jpaToDomainMapper::convertJpaToDomain);
  }

  @Override
  public boolean existsById(final CustomerId customerId) {
    final Long id = customerId.getValue();
    return jpaRepository.existsById(id);
  }

  @Override
  public Customer insert(final Customer customer) {
    final CustomerId customerId = customer.getId();
    checkDoesNotAlreadyExist(customerId);
    final CustomerJpaEntity customerJpaEntity = domainToJpaMapper.convertDomainToJpa(customer);
    final CustomerJpaEntity savedCustomerJpaEntity = jpaRepository.save(customerJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedCustomerJpaEntity);
  }

  private void checkDoesNotAlreadyExist(final CustomerId customerId) {
    final Long id = Optional.ofNullable(customerId)
                            .map(CustomerId::getValue)
                            .orElseThrow(CustomerMissingIdException::new);
    if (jpaRepository.existsById(id)) {
      throw new CustomerAlreadyExistsException(customerId);
    }
  }

  @Override
  public Customer update(final Customer customer) {
    final CustomerJpaEntity customerJpaEntity = domainToJpaMapper.convertDomainToJpa(customer);
    final CustomerJpaEntity savedCustomerJpaEntity = jpaRepository.save(customerJpaEntity);
    return jpaToDomainMapper.convertJpaToDomain(savedCustomerJpaEntity);
  }

  @Override
  public void deleteById(final CustomerId customerId) {
    jpaRepository.deleteById(customerId.getValue());
  }
}
