package com.acroteq.food.ordering.system.payment.service.data.access.creditentry.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "credit_entry")
@Entity
public class CreditEntryEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;
  @Column(name = "customer_id", length = 36)
  private String customerId;
  @Column(name = "total_credit_currency_id", length = 36)
  private String totalCreditCurrencyId;
  @Column(name = "total_credit_amount")
  private BigDecimal totalCreditAmount;
}
