package com.acroteq.food.ordering.system.restaurant.service.domain.entity;

import com.acroteq.food.ordering.system.domain.entity.BaseEntity;
import com.acroteq.food.ordering.system.domain.valueobject.CashValue;
import com.acroteq.food.ordering.system.domain.valueobject.OrderId;
import com.acroteq.food.ordering.system.domain.valueobject.OrderStatus;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class OrderDetail extends BaseEntity<OrderId> {

    private OrderStatus orderStatus;
    private CashValue totalAmount;
    private final List<Product> products;
}
