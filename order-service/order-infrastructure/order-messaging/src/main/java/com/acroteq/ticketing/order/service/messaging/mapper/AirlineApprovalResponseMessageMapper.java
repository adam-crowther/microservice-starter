package com.acroteq.ticketing.order.service.messaging.mapper;

import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.kafka.order.avro.model.AirlineApprovalResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, ValidationResultMapper.class })
public interface AirlineApprovalResponseMessageMapper {

  @Mapping(target = "result", source = "failureMessages")
  AirlineApprovalResponseDto convertMessageToDto(AirlineApprovalResponseMessage message);
}
