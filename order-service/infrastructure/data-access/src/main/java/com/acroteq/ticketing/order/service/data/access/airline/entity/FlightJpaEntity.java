package com.acroteq.ticketing.order.service.data.access.airline.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.ReplicatedJpaEntity;
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
@Table(name = "flights")
@Entity
public class FlightJpaEntity extends ReplicatedJpaEntity {

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "flight_price_currency_id", nullable = false)
  private String priceCurrencyId;

  @Column(name = "flight_price_amount", nullable = false)
  private BigDecimal priceAmount;
}
