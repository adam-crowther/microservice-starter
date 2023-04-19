package com.acroteq.ticketing.order.service.data.access.order.entity;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemJpaEntityId implements Serializable {

  @Column(name = "id")
  private Long id;

  @Column(name = "order_id")
  private Long orderId;
}
