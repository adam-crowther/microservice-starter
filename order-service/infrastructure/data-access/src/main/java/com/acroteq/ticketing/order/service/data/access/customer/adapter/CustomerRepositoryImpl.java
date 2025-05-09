package com.acroteq.ticketing.order.service.data.access.customer.adapter;

import com.acroteq.domain.valueobject.CustomerId;
import com.acroteq.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.mapper.CustomerJpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerRepositoryImpl extends ReadWriteRepositoryImpl<CustomerId, Customer, CustomerJpaEntity>
    implements CustomerRepository {

  private final CustomerJpaRepository jpaRepository;
  private final CustomerJpaMapper jpaMapper;

  public CustomerRepositoryImpl(final CustomerJpaRepository jpaRepository, final CustomerJpaMapper jpaMapper) {
    super(jpaRepository, jpaMapper);

    this.jpaRepository = jpaRepository;
    this.jpaMapper = jpaMapper;
  }

  @Override
  public Optional<Customer> findByUserName(final String userName) {
    return jpaRepository.findByUserName(userName)
                        .map(jpaMapper::convert);
  }
}
