package com.acroteq.ticketing.order.service.data.access.customer.mapper;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.JpaToDomainMapper;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class })
public interface CustomerJpaToDomainMapper extends JpaToDomainMapper<CustomerJpaEntity, Customer> {

  @Override
  Customer convertJpaToDomain(CustomerJpaEntity customer);
}
