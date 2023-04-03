package com.acroteq.food.ordering.system.order.service.messaging.mapper;

import com.acroteq.food.ordering.system.common.application.mapper.IdMapper;
import com.acroteq.food.ordering.system.common.application.mapper.ValidationResultMapper;
import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentResponseMessage;
import com.acroteq.food.ordering.system.order.service.domain.dto.message.PaymentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { IdMapper.class, ValidationResultMapper.class, PaymentStatusMapper.class })
public interface PaymentResponseMessageToDtoMapper {

  @Mapping(target = "paymentStatus", source = "paymentOrderStatus")
  @Mapping(target = "value.currencyId", source = "valueCurrencyId")
  @Mapping(target = "value.amount", source = "valueAmount")
  @Mapping(target = "result", source = "failureMessages")
  PaymentResponseDto convertMessageToDto(PaymentResponseMessage message);
}
