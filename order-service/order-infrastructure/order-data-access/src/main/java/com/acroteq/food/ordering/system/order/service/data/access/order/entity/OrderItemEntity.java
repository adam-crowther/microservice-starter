package com.acroteq.food.ordering.system.order.service.data.access.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.ALL;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(OrderItemEntityId.class)
@Table(name = "order_items")
@Entity
public class OrderItemEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private Long id;

  @EqualsAndHashCode.Include
  @Id
  @ManyToOne(cascade = ALL)
  @JoinColumn(name="order_id")
  private OrderEntity order;

  @Column(name = "product_id", length = 36)
  private String productId;

  @Column(name = "quantity")
  private Integer quantity;
}
