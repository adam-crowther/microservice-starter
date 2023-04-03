package com.acroteq.food.ordering.system.order.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.order.service.domain.dto.customer.CustomerDto;
import com.acroteq.food.ordering.system.order.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = IdMapper.class)
public interface CustomerDtoToDomainMapper {

  Customer convertDtoToDomain(CustomerDto customer);
}
