package com.acroteq.food.ordering.system.customer.service.application.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.customer.service.application.model.Customer;
import com.acroteq.food.ordering.system.customer.service.domain.dto.get.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = IdMapper.class)
public interface CustomerDtoToApiMapper {

  Customer convertDtoToApi(CustomerDto dto);
}
