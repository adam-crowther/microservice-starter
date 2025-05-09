package com.acroteq.ticketing.airline.service.data.access.airline.entity;

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
@Table(name = "flights")
@Entity
public class FlightJpaEntity extends PrimaryJpaEntity {

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "price_currency_id", nullable = false)
  private String priceCurrencyId;

  @Column(name = "price_amount", nullable = false)
  private BigDecimal priceAmount;

  @Column(name = "available", nullable = false)
  private Boolean available;
}
