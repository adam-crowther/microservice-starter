package com.acroteq.food.ordering.system.payment.service.messaging.mapper;

import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentOrderStatusMapper {

  com.acroteq.food.ordering.system.payment.service.domain.dto.PaymentOrderStatus convert(final PaymentOrderStatus status);
}
