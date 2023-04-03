package com.acroteq.food.ordering.system.restaurant.service.domain.event;

import com.acroteq.food.ordering.system.domain.event.DomainEvent;
import com.acroteq.food.ordering.system.domain.validation.ValidationResult;
import com.acroteq.food.ordering.system.domain.valueobject.RestaurantId;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.OrderApproval;
import com.acroteq.food.ordering.system.restaurant.service.domain.event.visitor.OrderApprovalEventVisitor;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
public abstract class OrderApprovalEvent extends DomainEvent<OrderApproval> {
    private final OrderApproval orderApproval;
    private final RestaurantId restaurantId;
    private final ValidationResult result;

    public abstract void accept(final OrderApprovalEventVisitor visitor);
}
