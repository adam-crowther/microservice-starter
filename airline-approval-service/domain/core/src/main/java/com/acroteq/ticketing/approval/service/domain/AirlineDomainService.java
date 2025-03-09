package com.acroteq.ticketing.approval.service.domain;

import com.acroteq.ticketing.approval.service.domain.entity.airline.Airline;
import com.acroteq.ticketing.approval.service.domain.entity.order.Order;
import com.acroteq.ticketing.approval.service.domain.valueobject.OrderApprovalOutput;

public interface AirlineDomainService {

  OrderApprovalOutput validateOrder(Order order);

  void validateAirline(Airline airline);
}
