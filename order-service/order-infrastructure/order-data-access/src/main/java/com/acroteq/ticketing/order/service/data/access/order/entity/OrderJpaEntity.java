package com.acroteq.ticketing.order.service.data.access.order.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.google.common.collect.ImmutableList;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "orders")
@Entity
public class OrderJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "airline_id")
  private Long airlineId;

  @Column(name = "tracking_id", length = 36)
  private String trackingId;

  @Column(name = "price_currency_id")
  private Long priceCurrencyId;

  @Column(name = "price_amount")
  private BigDecimal priceAmount;

  @Enumerated(STRING)
  @Column(name = "order_status")
  private OrderStatus orderStatus;

  @ElementCollection
  @CollectionTable(name = "order_failure_messages", joinColumns = @JoinColumn(name = "order_id"))
  @Column(name = "failure_message")
  private ImmutableList<String> failureMessages;

  @Embedded
  private OrderAddressJpaEntity address;

  @OneToMany(cascade = ALL)
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  private ImmutableList<OrderItemJpaEntity> items;

  @SuppressWarnings("PublicInnerClass")
  public static class OrderJpaEntityBuilder {

    public List<OrderItemJpaEntity> getItems() {
      return items;
    }

    public OrderAddressJpaEntity getAddress() {
      return address;
    }

    public OrderJpaEntityBuilder items(final List<OrderItemJpaEntity> items) {
      this.items = ImmutableList.copyOf(items);
      return this;
    }

    public OrderJpaEntityBuilder failureMessages(final List<String> failureMessages) {
      this.failureMessages = ImmutableList.copyOf(failureMessages);
      return this;
    }
  }
}
