package com.acroteq.ticketing.order.service.messaging.mapper.airline;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRejectedResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalRejectedResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, OrderIdMapper.class, ValidationResultMapper.class })
public interface AirlineApprovalRejectedResponseMessageMapper
    extends MessageToDtoMapper<AirlineApprovalRejectedResponseMessage, AirlineApprovalRejectedResponseDto> {

  @Override
  @Mapping(target = "result", source = "message.failureMessages")
  AirlineApprovalRejectedResponseDto convertMessageToDto(AirlineApprovalRejectedResponseMessage message,
                                                         Integer partition,
                                                         Long offset);
}
