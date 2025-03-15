package com.acroteq.ticketing.payment.service.data.access.credithistory.entity;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.PrimaryJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import com.acroteq.ticketing.payment.service.domain.valueobject.CreditChangeType;
import com.acroteq.ticketing.payment.service.domain.valueobject.TransactionType;
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
@Table(name = "credit_changes")
@Entity
public class CreditChangeJpaEntity extends PrimaryJpaEntity {

  @ManyToOne(optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  private CustomerJpaEntity customer;

  @Column(name = "credit_delta_currency_id", nullable = false)
  private String creditDeltaCurrencyId;

  @Column(name = "credit_delta_amount", nullable = false)
  private BigDecimal creditDeltaAmount;

  @Enumerated(STRING)
  @Column(name = "transaction_type", nullable = false)
  private TransactionType transactionType;

  @Enumerated(STRING)
  @Column(name = "credit_change_event_type", nullable = false)
  private CreditChangeType creditChangeType;
}
