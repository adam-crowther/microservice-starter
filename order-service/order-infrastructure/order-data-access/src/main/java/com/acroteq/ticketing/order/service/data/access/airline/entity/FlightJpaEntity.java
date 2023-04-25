package com.acroteq.ticketing.order.service.data.access.airline.entity;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Materialised view of the airline entity in the airline schema.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "flights")
@Entity
public class FlightJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private Long id;

  @Column(name = "flight_number")
  private String flightNumber;

  @Column(name = "flight_price_currency_id")
  private String priceCurrencyId;

  @Column(name = "flight_price_amount")
  private BigDecimal priceAmount;
}
