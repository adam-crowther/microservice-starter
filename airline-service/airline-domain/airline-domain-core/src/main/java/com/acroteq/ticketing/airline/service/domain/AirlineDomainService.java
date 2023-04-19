package com.acroteq.ticketing.airline.service.domain;

import com.acroteq.ticketing.airline.service.domain.entity.Order;
import com.acroteq.ticketing.airline.service.domain.valueobject.OrderApprovalOutput;

public interface AirlineDomainService {

  OrderApprovalOutput validateOrder(Order order);
}
