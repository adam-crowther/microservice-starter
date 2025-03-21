package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.DtoToDomainMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.id.CustomerIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.application.mapper.id.PaymentIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import com.acroteq.ticketing.payment.service.domain.resolver.CustomerResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { OrderIdMapper.class,
                 CustomerIdMapper.class,
                 PaymentIdMapper.class,
                 DateTimeMapper.class,
                 CustomerResolver.class })
public interface PaymentDtoToDomainMapper extends DtoToDomainMapper<PaymentRequestDto, Payment> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "audit", ignore = true)
  @Mapping(target = "customer", source = "customerId")
  @Mapping(target = "status", constant = "PENDING")
  @Override
  Payment convertDtoToDomain(PaymentRequestDto paymentRequestDto);
}
