package com.acroteq.food.ordering.system.customer.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { IdMapper.class })
public interface CustomerDomainToDtoMapper {

  CustomerDto convertDomainToDto(Customer customer);
}
