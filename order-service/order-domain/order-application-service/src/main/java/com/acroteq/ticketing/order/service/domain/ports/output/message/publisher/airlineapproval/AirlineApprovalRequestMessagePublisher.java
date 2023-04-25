package com.acroteq.ticketing.order.service.domain.ports.output.message.publisher.airlineapproval;

import com.acroteq.ticketing.domain.event.publisher.DomainEventPublisher;
import com.acroteq.ticketing.order.service.domain.event.OrderPaidEvent;

public interface AirlineApprovalRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {

}
