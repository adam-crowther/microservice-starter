package com.acroteq.ticketing.approval.service.messaging.mapper.approval;


import com.acroteq.application.mapper.DateTimeMapper;
import com.acroteq.application.mapper.MapstructConfig;
import com.acroteq.application.mapper.ValidationResultMapper;
import com.acroteq.application.mapper.id.AirlineIdMapper;
import com.acroteq.application.mapper.id.OrderIdMapper;
import com.acroteq.infrastructure.mapper.EventToMessageMapper;
import com.acroteq.ticketing.approval.service.domain.event.order.OrderApprovedEvent;
import com.acroteq.ticketing.kafka.flight.approval.avro.model.AirlineApprovalApprovedResponseMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(config = MapstructConfig.class,
        uses = { AirlineIdMapper.class, OrderIdMapper.class, DateTimeMapper.class, ValidationResultMapper.class },
        imports = UUID.class)
public interface AirlineApprovalApprovedResponseMessageFactory
    extends EventToMessageMapper<OrderApprovedEvent, AirlineApprovalApprovedResponseMessage> {

  @Mapping(target = "orderId", source = "orderApproval.orderId")
  @Mapping(target = "orderVersion", source = "orderApproval.orderVersion")
  @Mapping(target = "audit", source = "orderApproval.audit")
  @Mapping(target = "airlineId", source = "orderApproval.airline.id")
  @Mapping(target = "auditBuilder", ignore = true)
  @Override
  AirlineApprovalApprovedResponseMessage convert(OrderApprovedEvent event);
}
