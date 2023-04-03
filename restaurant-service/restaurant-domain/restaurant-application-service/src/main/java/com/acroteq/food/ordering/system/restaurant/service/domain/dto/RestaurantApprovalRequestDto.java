package com.acroteq.food.ordering.system.restaurant.service.domain.dto;

import com.acroteq.food.ordering.system.domain.valueobject.RestaurantOrderStatus;
import com.acroteq.food.ordering.system.restaurant.service.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@AllArgsConstructor(access = PRIVATE)
public class RestaurantApprovalRequestDto {
    private String id;
    private String sagaId;
    private String restaurantId;
    private String orderId;
    private RestaurantOrderStatus restaurantOrderStatus;
    private java.util.List<Product> products;
    private String priceCurrencyId;
    private java.math.BigDecimal priceAmount;
    private java.time.Instant createdDateTime;
}
