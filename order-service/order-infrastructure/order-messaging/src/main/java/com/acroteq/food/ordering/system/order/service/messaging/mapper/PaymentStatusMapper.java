package com.acroteq.food.ordering.system.order.service.messaging.mapper;

import com.acroteq.food.ordering.system.kafka.order.avro.model.PaymentOrderStatus;
import com.acroteq.food.ordering.system.order.service.domain.dto.common.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

import static org.mapstruct.MappingConstants.NULL;

@Mapper
public interface PaymentStatusMapper {

  @ValueMappings({
      @ValueMapping(target = NULL, source = "PENDING"),
  })
  PaymentStatus convert(final PaymentOrderStatus status);
}
