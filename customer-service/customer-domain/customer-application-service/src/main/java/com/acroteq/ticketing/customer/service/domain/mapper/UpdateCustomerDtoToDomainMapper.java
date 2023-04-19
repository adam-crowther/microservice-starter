package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = { CustomerIdMapper.class, DateTimeMapper.class })
public interface UpdateCustomerDtoToDomainMapper {

  Customer convertDtoToDomain(UpdateCustomerCommandDto createCustomerCommandDto);
}
