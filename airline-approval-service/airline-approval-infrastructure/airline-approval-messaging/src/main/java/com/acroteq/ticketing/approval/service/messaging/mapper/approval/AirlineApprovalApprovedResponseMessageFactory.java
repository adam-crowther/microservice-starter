package com.acroteq.ticketing.approval.service.messaging.mapper.approval;


import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface AirlineApprovalApprovedResponseMessageFactory
    extends EventToMessageMapper<OrderApprovedEvent, AirlineApprovalApprovedResponseMessage> {

  @Mapping(target = "orderId", source = "orderApproval.orderId")
  @Mapping(target = "airlineId", source = "orderApproval.airline.id")
  @Override
  AirlineApprovalApprovedResponseMessage convertEventToMessage(OrderApprovedEvent event);
}
