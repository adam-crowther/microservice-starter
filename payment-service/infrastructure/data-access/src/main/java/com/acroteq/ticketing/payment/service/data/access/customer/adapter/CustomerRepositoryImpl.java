package com.acroteq.ticketing.payment.service.data.access.customer.adapter;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.infrastructure.data.access.repository.ReadWriteRepositoryImpl;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerDomainToJpaMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.mapper.CustomerJpaToDomainMapper;
import com.acroteq.ticketing.payment.service.data.access.customer.repository.CustomerJpaRepository;
import com.acroteq.ticketing.payment.service.domain.entity.Customer;
import com.acroteq.ticketing.payment.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
public class CustomerRepositoryImpl extends ReadWriteRepositoryImpl<CustomerId, Customer, CustomerJpaEntity>
    implements CustomerRepository {

  public CustomerRepositoryImpl(final CustomerJpaRepository jpaRepository,
                                final CustomerJpaToDomainMapper jpaToDomainMapper,
                                final CustomerDomainToJpaMapper domainToJpaMapper) {
    super(jpaRepository, jpaToDomainMapper, domainToJpaMapper);
  }
}
