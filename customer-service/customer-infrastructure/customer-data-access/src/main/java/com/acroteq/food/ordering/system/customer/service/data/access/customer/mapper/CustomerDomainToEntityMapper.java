package com.acroteq.food.ordering.system.customer.service.data.access.customer.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.data.access.customer.entity.CustomerEntity;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = IdMapper.class)
public interface CustomerDomainToEntityMapper {

  CustomerEntity convertDomainToEntity(Customer customer);
}
