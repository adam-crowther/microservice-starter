package com.acroteq.application.mapper.id;

import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.domain.valueobject.PaymentId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class, imports = UUID.class)
public interface PaymentIdMapper extends IdMapper<PaymentId> {

  @Mapping(target = "value", source = "id")
  @Override
  PaymentId convertStringToId(String id);

  @Mapping(target = "value", source = "id")
  @Override
  PaymentId convertLongToId(Long id);
}
