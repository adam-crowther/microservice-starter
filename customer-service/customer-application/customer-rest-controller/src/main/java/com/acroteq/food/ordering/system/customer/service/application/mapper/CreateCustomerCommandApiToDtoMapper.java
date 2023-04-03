package com.acroteq.food.ordering.system.customer.service.application.mapper;

import com.acroteq.food.ordering.system.customer.service.application.model.CreateCustomerCommand;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCustomerCommandApiToDtoMapper {

  CreateCustomerCommandDto convertApiToDto(CreateCustomerCommand createCustomerCommand);
}
