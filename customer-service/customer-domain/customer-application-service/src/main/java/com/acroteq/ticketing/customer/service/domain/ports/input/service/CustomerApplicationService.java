package com.acroteq.ticketing.customer.service.domain.ports.input.service;

import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.ticketing.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.ticketing.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.ticketing.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import jakarta.validation.Valid;

public interface CustomerApplicationService {

  CustomerDto getCustomer(Long customerId);

  CreateCustomerResponseDto createCustomer(@Valid CreateCustomerCommandDto createCustomerCommandDto);

  void updateCustomer(@Valid UpdateCustomerCommandDto createCustomerCommandDto);

  void deleteCustomer(Long customerId);
}
