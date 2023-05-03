package com.acroteq.ticketing.order.service.data.access.order.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.domain.valueobject.OrderStatus;
import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "orders")
@Entity
public class OrderJpaEntity extends MasterJpaEntity {

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "airline_id")
  private Long airlineId;

  @Column(name = "tracking_id", length = 36)
  private String trackingId;

  @Column(name = "price_currency_id")
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

  @Embedded
  private OrderAddressJpaEmbedded address;

  @OneToMany(cascade = ALL)
  @JoinColumn(name = "owner_id", referencedColumnName = "id")
  private List<OrderItemJpaEntity> items;
}
