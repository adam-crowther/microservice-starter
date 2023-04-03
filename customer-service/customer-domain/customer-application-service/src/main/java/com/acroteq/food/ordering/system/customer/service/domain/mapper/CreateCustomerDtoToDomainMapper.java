package com.acroteq.food.ordering.system.customer.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class, DateTimeMapper.class })
public interface CreateCustomerDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  Customer convertDtoToDomain(final CreateCustomerCommandDto createCustomerCommandDto);
}
