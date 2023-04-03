package com.acroteq.food.ordering.system.order.service.data.access.customer.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.data.access.customer.entity.CustomerEntity;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { IdMapper.class })
public interface CustomerEntityToDomainMapper {

  Customer convertEntityToDomain(CustomerEntity customer);
}
