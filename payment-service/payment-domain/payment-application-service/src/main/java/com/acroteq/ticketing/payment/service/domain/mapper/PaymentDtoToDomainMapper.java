package com.acroteq.ticketing.payment.service.domain.mapper;

import com.acroteq.ticketing.application.mapper.CustomerIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.PaymentIdMapper;
import com.acroteq.ticketing.payment.service.domain.dto.payment.PaymentRequestDto;
import com.acroteq.ticketing.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { OrderIdMapper.class, CustomerIdMapper.class, PaymentIdMapper.class, DateTimeMapper.class })
public interface PaymentDtoToDomainMapper {

  @Mapping(target = "id", ignore = true)
  Payment convertDtoToDomain(PaymentRequestDto paymentRequestDto);
}
