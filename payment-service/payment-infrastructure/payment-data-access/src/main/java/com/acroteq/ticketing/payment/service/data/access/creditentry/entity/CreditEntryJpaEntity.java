package com.acroteq.ticketing.payment.service.data.access.creditentry.entity;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "credit_entry")
@Entity
public class CreditEntryJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "total_credit_currency_id")
  private String totalCreditCurrencyId;

  @Column(name = "total_credit_amount")
  private BigDecimal totalCreditAmount;
}
