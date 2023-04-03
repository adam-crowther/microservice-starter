package com.acroteq.food.ordering.system.payment.service.data.access.credithistory.entity;

import com.acroteq.food.ordering.system.payment.service.domain.valueobject.TransactionType;
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

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "credit_history")
@Entity
public class CreditHistoryEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;
  @Column(name = "customer_id", length = 36)
  private String customerId;
  @Column(name = "credit_currency_id", length = 36)
  private String creditCurrencyId;
  @Column(name = "credit_amount")
  private BigDecimal creditAmount;
  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private TransactionType type;
}
