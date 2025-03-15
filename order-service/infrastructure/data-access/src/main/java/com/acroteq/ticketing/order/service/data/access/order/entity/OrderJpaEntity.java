package com.acroteq.ticketing.order.service.data.access.order.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.domain.valueobject.OrderStatus;
import com.acroteq.infrastructure.data.access.entity.PrimaryJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.entity.AirlineJpaEntity;
import com.acroteq.ticketing.order.service.data.access.customer.entity.CustomerJpaEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
@Entity
public class OrderJpaEntity extends PrimaryJpaEntity {

  @ManyToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerJpaEntity customer;

  @ManyToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "airline_id", nullable = false)
  private AirlineJpaEntity airline;

  @Column(name = "tracking_id", length = 36, nullable = false)
  private String trackingId;

  @Column(name = "price_currency_id", nullable = false)
  private String priceCurrencyId;

  @Column(name = "price_amount", nullable = false)
  private BigDecimal priceAmount;

  @Enumerated(STRING)
  @Column(name = "order_status", nullable = false)
  private OrderStatus orderStatus;

  @ElementCollection
  @CollectionTable(name = "order_failure_messages", joinColumns = @JoinColumn(name = "order_id"))
  @Column(name = "failure_message", nullable = false)
  private List<String> failureMessages;

  @Embedded
  private OrderAddressJpaEmbedded address;

  @OneToMany(cascade = ALL, orphanRemoval = true)
  @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
  private List<OrderItemJpaEntity> items;
}
