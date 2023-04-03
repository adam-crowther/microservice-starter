package com.acroteq.food.ordering.system.customer.service.application.mapper;

import com.acroteq.food.ordering.system.customer.service.application.model.UpdateCustomerCommand;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface UpdateCustomerCommandApiToDtoMapper {

  UpdateCustomerCommandDto convertApiToDto(UpdateCustomerCommand updateCustomerCommand);
}
