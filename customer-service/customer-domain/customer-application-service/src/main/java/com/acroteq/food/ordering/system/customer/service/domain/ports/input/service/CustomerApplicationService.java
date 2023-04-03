package com.acroteq.food.ordering.system.customer.service.domain.ports.input.service;

import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerCommandDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.create.CreateCustomerResponseDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.get.CustomerDto;
import com.acroteq.food.ordering.system.customer.service.domain.dto.update.UpdateCustomerCommandDto;
import jakarta.validation.Valid;

import java.util.UUID;

public interface CustomerApplicationService {

  CustomerDto getCustomer(UUID customerId);

  CreateCustomerResponseDto createCustomer(@Valid CreateCustomerCommandDto createCustomerCommandDto);

  void updateCustomer(@Valid UpdateCustomerCommandDto createCustomerCommandDto);

  void deleteCustomer(UUID customerId);
}
