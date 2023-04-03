package com.acroteq.food.ordering.system.customer.service.domain.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.DateTimeMapper;
import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { IdMapper.class, DateTimeMapper.class })
public interface UpdateCustomerDtoToDomainMapper {

  Customer convertDtoToDomain(final UpdateCustomerCommandDto createCustomerCommandDto);
}
