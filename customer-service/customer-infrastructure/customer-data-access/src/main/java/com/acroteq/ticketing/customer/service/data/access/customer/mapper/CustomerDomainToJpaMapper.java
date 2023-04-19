package com.acroteq.ticketing.customer.service.data.access.customer.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerDomainToJpaMapper {

  CustomerJpaEntity convertDomainToEntity(Customer customer);
}
