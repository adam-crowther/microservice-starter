package com.acroteq.ticketing.customer.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class, uses = { CustomerIdMapper.class, DateTimeMapper.class })
public interface UpdateCustomerDtoToDomainMapper extends DtoToDomainMapper<UpdateCustomerCommandDto, Customer> {

  @Mapping(target = "audit", ignore = true)
  @Override
  Customer convertDtoToDomain(UpdateCustomerCommandDto createCustomerCommandDto);
}
