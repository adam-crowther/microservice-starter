package com.acroteq.ticketing.payment.service.data.access.payment.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
public class PaymentJpaEntity extends MasterJpaEntity {

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "order_version")
  private Long orderVersion;

  @Column(name = "value_currency_id")
  private String valueCurrencyId;

  @Column(name = "value_amount")
  private BigDecimal valueAmount;

  @Enumerated(STRING)
  @Column(name = "status")
  private PaymentStatus status;
}
