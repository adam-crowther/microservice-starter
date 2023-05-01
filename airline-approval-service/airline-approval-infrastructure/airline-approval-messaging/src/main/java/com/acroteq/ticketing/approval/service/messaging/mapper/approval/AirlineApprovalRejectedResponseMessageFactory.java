package com.acroteq.ticketing.approval.service.messaging.mapper.approval;


import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.application.mapper.id.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.id.OrderIdMapper;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalRejectedResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface AirlineApprovalRejectedResponseMessageFactory
    extends EventToMessageMapper<OrderRejectedEvent, AirlineApprovalRejectedResponseMessage> {

  @Mapping(target = "orderId", source = "event.orderApproval.orderId")
  @Mapping(target = "airlineId", source = "event.orderApproval.airline.id")
  @Mapping(target = "failureMessages", source = "event.result")
  @Override
  AirlineApprovalRejectedResponseMessage convertEventToMessage(OrderRejectedEvent event);
}
