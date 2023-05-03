package com.acroteq.ticketing.order.service.data.access.order.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "order_items")
@Entity
public class OrderItemJpaEntity extends MasterJpaEntity {

  @Column(name = "owner_id")
  private Long orderId;

  @Column(name = "flight_id")
  private Long flightId;

  @Column(name = "quantity")
  private Integer quantity;
}
