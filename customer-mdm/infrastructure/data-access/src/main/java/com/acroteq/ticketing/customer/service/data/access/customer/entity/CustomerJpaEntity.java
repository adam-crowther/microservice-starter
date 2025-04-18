package com.acroteq.ticketing.customer.service.data.access.customer.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.PrimaryJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "customers")
@Entity
public class CustomerJpaEntity extends PrimaryJpaEntity {

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "credit_limit_currency_id", nullable = false)
  private String creditLimitCurrencyId;

  @Column(name = "credit_limit_amount", nullable = false)
  private BigDecimal creditLimitAmount;
}
