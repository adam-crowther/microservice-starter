package com.acroteq.ticketing.customer.service.application.mapper;

import com.acroteq.ticketing.customer.service.application.model.CreateCustomerCommand;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface CreateCustomerCommandApiToDtoMapper {

  CreateCustomerCommandDto convertApiToDto(CreateCustomerCommand createCustomerCommand);
}
