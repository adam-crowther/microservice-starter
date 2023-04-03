package com.acroteq.food.ordering.system.order.service.data.access.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

/**
 * Materialised view of the restaurant entity in the restaurant schema.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(RestaurantEntityId.class)
@Table(name = "order_restaurant_m_view", schema = "restaurant")
@Entity
public class RestaurantEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "restaurant_id", length = 36)
  private String restaurantId;

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "product_id", length = 36)
  private String productId;

  @Column(name = "restaurant_name")
  private String restaurantName;

  @Column(name = "restaurant_active")
  private boolean restaurantActive;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price_currency_id", length = 36)
  private String productPriceCurrencyId;

  @Column(name = "product_price_amount")
  private BigDecimal productPriceAmount;
}
