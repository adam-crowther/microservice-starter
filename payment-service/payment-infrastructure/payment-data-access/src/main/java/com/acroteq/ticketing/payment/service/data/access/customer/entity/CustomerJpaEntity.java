package com.acroteq.ticketing.payment.service.data.access.customer.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.ReplicatedJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "customers")
@Entity
public class CustomerJpaEntity extends ReplicatedJpaEntity {

  @Column(name = "credit_limit_currency_id", nullable = false)
  private String creditLimitCurrencyId;

  @Column(name = "credit_limit_amount", nullable = false)
  private BigDecimal creditLimitAmount;
}
