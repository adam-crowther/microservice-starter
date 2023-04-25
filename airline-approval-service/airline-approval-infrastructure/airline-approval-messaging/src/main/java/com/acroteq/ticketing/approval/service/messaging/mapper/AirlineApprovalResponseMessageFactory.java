package com.acroteq.ticketing.approval.service.messaging.mapper;


import com.acroteq.ticketing.application.mapper.AirlineIdMapper;
import com.acroteq.ticketing.application.mapper.DateTimeMapper;
import com.acroteq.ticketing.application.mapper.OrderIdMapper;
import com.acroteq.ticketing.application.mapper.ValidationResultMapper;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderRejectedEvent;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(uses = { AirlineIdMapper.class, OrderIdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface AirlineApprovalResponseMessageFactory {

  @Mapping(target = "orderId", source = "event.orderApproval.orderId")
  @Mapping(target = "airlineId", source = "event.orderApproval.airline.id")
  @Mapping(target = "orderApprovalStatus", source = "event.orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  AirlineApprovalResponseMessage createAirlineApprovalResponseMessage(OrderApprovedEvent event);

  @Mapping(target = "orderId", source = "event.orderApproval.orderId")
  @Mapping(target = "airlineId", source = "event.orderApproval.airline.id")
  @Mapping(target = "orderApprovalStatus", source = "event.orderApproval.approvalStatus")
  @Mapping(target = "failureMessages", source = "event.result")
  AirlineApprovalResponseMessage createAirlineApprovalResponseMessage(OrderRejectedEvent event);
}
