package com.acroteq.food.ordering.system.order.service.data.access.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "order_addresses")
@Entity
public class OrderAddressEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;

  @OneToOne(cascade = ALL)
  @JoinColumn(name="order_id")
  private OrderEntity order;

  @Column(name = "street")
  private String street;

  @Column(name = "postal_code")
  private String postalCode;

  @Column(name = "city")
  private String city;
}
