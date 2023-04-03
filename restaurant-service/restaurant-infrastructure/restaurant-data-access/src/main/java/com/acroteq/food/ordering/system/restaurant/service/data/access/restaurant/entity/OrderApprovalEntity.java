package com.acroteq.food.ordering.system.restaurant.service.data.access.restaurant.entity;

import com.acroteq.food.ordering.system.domain.valueobject.OrderApprovalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = PRIVATE)
@RequiredArgsConstructor
@Table(name = "order_approval", schema = "restaurant")
@Entity
public class OrderApprovalEntity {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id", length = 36)
    private String id;
    @Column(name = "restaurant_id", length = 36)
    private String restaurantId;
    @Column(name = "order_id", length = 36)
    private String orderId;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderApprovalStatus status;
}
