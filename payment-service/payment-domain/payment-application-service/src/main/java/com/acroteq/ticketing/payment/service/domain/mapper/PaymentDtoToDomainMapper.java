package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.DtoToDomainMapper;
import com.acroteq.ticketing.application.mapper.id.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.id.PaymentIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class, CustomerIdMapper.class, PaymentIdMapper.class, DateTimeMapper.class })
public interface PaymentDtoToDomainMapper extends DtoToDomainMapper<PaymentRequestDto, Payment> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "paymentStatus", constant = "PENDING")
  @Override
  Payment convertDtoToDomain(PaymentRequestDto paymentRequestDto);
}
