package com.acroteq.ticketing.order.service.data.access.order.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(OrderItemJpaEntityId.class)
@Table(name = "order_items")
@Entity
public class OrderItemJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @EqualsAndHashCode.Include
  @Setter
  @Id
  @Column(name = "owner_id")
  private Long orderId;

  @Column(name = "flight_id")
  private Long flightId;

  @Column(name = "quantity")
  private Integer quantity;
}
