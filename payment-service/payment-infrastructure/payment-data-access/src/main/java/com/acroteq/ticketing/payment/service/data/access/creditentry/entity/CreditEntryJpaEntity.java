package com.acroteq.ticketing.payment.service.data.access.creditentry.entity;

import static jakarta.persistence.CascadeType.MERGE;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.MasterJpaEntity;
import com.acroteq.ticketing.payment.service.data.access.customer.entity.CustomerJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "credit_entry")
@Entity
public class CreditEntryJpaEntity extends MasterJpaEntity {

  @Column(name = "total_credit_currency_id", nullable = false)
  private String totalCreditCurrencyId;

  @Column(name = "total_credit_amount", nullable = false)
  private BigDecimal totalCreditAmount;

  @OneToOne(cascade = MERGE, optional = false)
  @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
  private CustomerJpaEntity customer;
}
