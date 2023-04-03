package com.acroteq.food.ordering.system.restaurant.service.domain;

import com.acroteq.food.ordering.system.restaurant.service.domain.dto.RestaurantApprovalRequestDto;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.OrderApprovalEvent;
import com.acroteq.food.ordering.system.restaurant.service.domain.ports.input.message.listener.RestaurantApprovalRequestMessageListener;
import com.acroteq.food.ordering.system.restaurant.service.domain.visitor.visitor.OrderApprovalEventPublisherVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestaurantApprovalRequestMessageListenerImpl implements RestaurantApprovalRequestMessageListener {

    private final RestaurantApprovalRequestHelper restaurantApprovalRequestHelper;
    private final OrderApprovalEventPublisherVisitor orderApprovalEventPublisherVisitor;

    @Override
    public void approveOrder(final RestaurantApprovalRequestDto restaurantApprovalRequest) {
        final OrderApprovalEvent orderApprovalEvent =
                restaurantApprovalRequestHelper.persistOrderApproval(restaurantApprovalRequest);
        orderApprovalEvent.accept(orderApprovalEventPublisherVisitor);
    }
}
