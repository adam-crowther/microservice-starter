package com.acroteq.ticketing.order.service.domain;

import com.acroteq.ticketing.domain.valueobject.CustomerId;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerCreatedDto;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerDeletedDto;
import com.acroteq.ticketing.order.service.domain.dto.customer.CustomerUpdatedDto;
import com.acroteq.ticketing.order.service.domain.entity.Customer;
import com.acroteq.ticketing.order.service.domain.mapper.customer.CustomerCreatedDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.mapper.customer.CustomerDeletedDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.mapper.customer.CustomerUpdatedDtoToDomainMapper;
import com.acroteq.ticketing.order.service.domain.ports.input.message.listener.customer.CustomerEventMessageListener;
import com.acroteq.ticketing.order.service.domain.ports.output.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerEventMessageListenerImpl implements CustomerEventMessageListener {

  private final CustomerRepository customerRepository;
  private final CustomerCreatedDtoToDomainMapper createdMapper;
  private final CustomerUpdatedDtoToDomainMapper updatedMapper;
  private final CustomerDeletedDtoToDomainMapper deletedMapper;

  @Transactional
  @Override
  public void customerCreated(final CustomerCreatedDto dto) {
    log.info("Creating Customer: {}", dto.getId());
    final Customer customer = createdMapper.convertDtoToDomain(dto);
    customerRepository.insert(customer);
  }

  @Transactional
  @Override
  public void customerUpdated(final CustomerUpdatedDto dto) {
    log.info("Updating Customer: {}", dto.getId());
    final Customer customer = updatedMapper.convertDtoToDomain(dto);
    customerRepository.update(customer);
  }

  @Transactional
  @Override
  public void customerDeleted(final CustomerDeletedDto dto) {
    final CustomerId customerId = deletedMapper.convertDtoToDomain(dto);
    log.info("Deleting Customer: {}", customerId);
    customerRepository.deleteById(customerId);
  }
}
