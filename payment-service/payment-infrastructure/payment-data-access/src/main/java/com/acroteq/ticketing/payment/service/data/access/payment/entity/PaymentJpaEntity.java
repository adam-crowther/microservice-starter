package com.acroteq.ticketing.payment.service.data.access.payment.entity;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.infrastructure.data.access.entity.PrimaryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "payments")
@Entity
public class PaymentJpaEntity extends PrimaryJpaEntity {

  @ManyToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerJpaEntity customer;

  @Column(name = "order_id", nullable = false)
  private Long orderId;

  @Column(name = "order_version", nullable = false)
  private Long orderVersion;

  @Column(name = "value_currency_id", nullable = false)
  private String valueCurrencyId;

  @Column(name = "value_amount", nullable = false)
  private BigDecimal valueAmount;

  @Enumerated(STRING)
  @Column(name = "status", nullable = false)
  private PaymentStatus status;
}
