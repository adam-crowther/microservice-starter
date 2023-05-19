package com.acroteq.ticketing.order.service.messaging.mapper.airline;

import com.acroteq.ticketing.application.mapper.MapstructConfig;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.infrastructure.mapper.MessageToDtoMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import com.acroteq.ticketing.order.service.domain.dto.message.AirlineApprovalApprovedResponseDto;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, OrderIdMapper.class, ValidationResultMapper.class })
public interface AirlineApprovalApprovedResponseMessageMapper
    extends MessageToDtoMapper<AirlineApprovalApprovedResponseMessage, AirlineApprovalApprovedResponseDto> {

  @Override
  AirlineApprovalApprovedResponseDto convertMessageToDto(AirlineApprovalApprovedResponseMessage message,
                                                         Integer partition,
                                                         Long offset);
}
