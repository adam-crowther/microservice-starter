package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CurrencyIdMapper;
import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.customer.CustomerDto;
import com.acroteq.ticketing.payment.service.domain.entity.CreditEntry;
import com.acroteq.ticketing.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { CurrencyIdMapper.class, CustomerIdMapper.class })
public interface CustomerDtoToDomainMapper {

  @Mapping(target = "totalCredit", source = "creditLimit")
  CreditEntry convertDtoToCreditEntryDomain(CustomerDto customerDto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customerId", source = "id")
  @Mapping(target = "credit", source = "creditLimit")
  @Mapping(target = "transactionType", constant = "CREDIT")
  @Mapping(target = "creditHistoryEventType", constant = "CREDIT_LIMIT_CHANGE")
  CreditHistory convertDtoToCreditHistoryDomain(CustomerDto customerDto);
}
