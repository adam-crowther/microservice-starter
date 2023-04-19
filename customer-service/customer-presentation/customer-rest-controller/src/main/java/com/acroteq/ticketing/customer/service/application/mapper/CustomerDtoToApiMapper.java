package com.acroteq.ticketing.customer.service.application.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.application.model.Customer;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = CustomerIdMapper.class)
public interface CustomerDtoToApiMapper {

  Customer convertDtoToApi(CustomerDto dto);
}
