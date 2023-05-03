package com.acroteq.ticketing.payment.service.data.access.credithistory.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditHistoryEventType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
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
@Table(name = "credit_history")
@Entity
public class CreditHistoryJpaEntity extends MasterJpaEntity {

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
