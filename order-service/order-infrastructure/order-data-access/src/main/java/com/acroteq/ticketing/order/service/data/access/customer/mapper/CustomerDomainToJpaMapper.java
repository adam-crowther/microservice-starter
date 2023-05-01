package com.acroteq.ticketing.order.service.data.access.customer.mapper;

import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.DomainToJpaMapper;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { CustomerIdMapper.class })
public interface CustomerDomainToJpaMapper extends DomainToJpaMapper<Customer, CustomerJpaEntity> {

  @Override
  CustomerJpaEntity convertDomainToJpa(Customer customer);
}
