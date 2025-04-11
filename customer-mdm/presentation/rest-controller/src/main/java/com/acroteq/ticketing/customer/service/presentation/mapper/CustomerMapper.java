package com.acroteq.ticketing.customer.service.presentation.mapper;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.common.application.mapper.ApiToEntityMapper;
import com.acroteq.common.application.mapper.EntityToApiMapper;
import com.acroteq.domain.valueobject.CashValue;
import com.acroteq.domain.valueobject.CurrencyId;
import com.acroteq.ticketing.customer.service.domain.entity.Customer;
import com.acroteq.ticketing.customer.service.domain.entity.Customer.CustomerBuilder;
import com.acroteq.ticketing.customer.service.presentation.model.CreateCustomer;
import com.acroteq.ticketing.customer.service.presentation.model.UpdateCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(config = MapstructConfig.class,
        uses = { CustomerIdMapper.class, AuditMapper.class },
        imports = java.util.Collections.class)
public abstract class CustomerMapper
    implements EntityToApiMapper<Customer, com.acroteq.ticketing.customer.service.presentation.model.Customer>,
    ApiToEntityMapper<CreateCustomer, UpdateCustomer, Customer> {

  @Mapping(target = "creditLimitAmount", source = "creditLimit.amount")
  @Mapping(target = "creditLimitCurrencyId", source = "creditLimit.currencyId.value")
  @Override
  public abstract com.acroteq.ticketing.customer.service.presentation.model.Customer convert(Customer entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditLimit",
           expression = "java(convertCreditLimit(command.getCreditLimitCurrencyId(), command.getCreditLimitAmount()))")
  @Override
  public abstract Customer convert(CreateCustomer command);

  @Override
  public Customer convert(final UpdateCustomer command, final Customer existing) {
    return convert(command, existing.toBuilder()).build();
  }

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "creditLimit",
           expression = "java(convertCreditLimit(command.getCreditLimitCurrencyId(), command.getCreditLimitAmount()))")
  public abstract CustomerBuilder<?, ?> convert(UpdateCustomer command, @MappingTarget CustomerBuilder<?, ?> existing);

  protected CashValue convertCreditLimit(final String currencyId, final double amount) {
    return CashValue.builder()
                    .currencyId(CurrencyId.of(currencyId))
                    .amount(BigDecimal.valueOf(amount))
                    .build();

  }
}
