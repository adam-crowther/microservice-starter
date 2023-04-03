package com.acroteq.food.ordering.system.order.service.data.access.order.entity;

import com.acroteq.food.ordering.system.order.service.domain.dto.common.OrderStatus;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "orders")
@Entity
public class OrderEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;

  @Column(name = "customer_id", length = 36)
  private String customerId;

  @Column(name = "restaurant_id", length = 36)
  private String restaurantId;

  @Column(name = "tracking_id", length = 36)
  private String trackingId;

  @Column(name = "price_currency_id", length = 36)
  private String priceCurrencyId;

  @Column(name = "price_amount")
  private BigDecimal priceAmount;

  @Enumerated(STRING)
  @Column(name = "order_status")
  private OrderStatus orderStatus;

  @ElementCollection
  @CollectionTable(name = "order_failure_messages", joinColumns = @JoinColumn(name = "order_id"))
  @Column(name = "failure_message")
  private List<String> failureMessages;

  @OneToOne(mappedBy = "order", cascade = ALL)
  private OrderAddressEntity address;

  @OneToMany(mappedBy = "order", cascade = ALL)
  private List<OrderItemEntity> items;

  public class OrderEntityBuilder {
    public List<OrderItemEntity> getItems() {
      return items;
    }

    public OrderAddressEntity getAddress() {
      return address;
    }
  }

}
