package com.acroteq.food.ordering.system.payment.service.data.access.payment.entity;

import com.acroteq.food.ordering.system.domain.valueobject.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "payments")
@Entity
public class PaymentEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;
  @Column(name = "customer_id", length = 36)
  private String customerId;
  @Column(name = "order_id", length = 36)
  private String orderId;
  @Column(name = "value_currency_id", length = 36)
  private String valueCurrencyId;
  @Column(name = "value_amount")
  private BigDecimal valueAmount;
  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PaymentStatus status;
  @Column(name = "created_date_time")
  private ZonedDateTime createdDateTime;
}
