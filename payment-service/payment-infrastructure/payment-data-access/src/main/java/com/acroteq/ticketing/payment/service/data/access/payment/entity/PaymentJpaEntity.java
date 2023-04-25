package com.acroteq.ticketing.payment.service.data.access.payment.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.domain.valueobject.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "payments")
@Entity
public class PaymentJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "value_currency_id")
  private String valueCurrencyId;

  @Column(name = "value_amount")
  private BigDecimal valueAmount;

  @Enumerated(STRING)
  @Column(name = "status")
  private PaymentStatus status;

  @Column(name = "created_date_time")
  private ZonedDateTime createdDateTime;
}
