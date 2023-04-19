package com.acroteq.ticketing.customer.service.application.mapper;

import com.acroteq.ticketing.customer.service.application.model.UpdateCustomerCommand;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import org.mapstruct.Mapper;

@Mapper
public interface UpdateCustomerCommandApiToDtoMapper {

  UpdateCustomerCommandDto convertApiToDto(UpdateCustomerCommand updateCustomerCommand);
}
