package com.acroteq.food.ordering.system.order.service.data.access.order.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemEntityId implements Serializable {

  private Long id;

  private OrderEntity order;
}
