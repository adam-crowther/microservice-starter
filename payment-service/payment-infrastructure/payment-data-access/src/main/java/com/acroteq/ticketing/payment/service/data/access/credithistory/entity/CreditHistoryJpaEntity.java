package com.acroteq.ticketing.payment.service.data.access.credithistory.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
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

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "credit_history")
@Entity
public class CreditHistoryJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "customer_id")
  private Long customerId;

  @Column(name = "credit_currency_id")
  private String creditCurrencyId;

  @Column(name = "credit_amount")
  private BigDecimal creditAmount;

  @Enumerated(STRING)
  @Column(name = "transaction_type")
  private TransactionType transactionType;

  @Enumerated(STRING)
  @Column(name = "credit_history_event_type")
  private CreditHistoryEventType creditHistoryEventType;
}
