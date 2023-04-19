package com.acroteq.ticketing.order.service.data.access.airline.entity;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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
@IdClass(AirlineJpaEntityId.class)
@Table(name = "order_airline_m_view", schema = "airline")
@Entity
public class AirlineJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id")
  private Long airlineId;

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "flight_id")
  private Long flightId;

  @Column(name = "airline_name")
  private String airlineName;

  @Column(name = "airline_active")
  private boolean airlineActive;

  @Column(name = "flight_number")
  private String flightNumber;

  @Column(name = "flight_price_currency_id")
  private Long flightPriceCurrencyId;

  @Column(name = "flight_price_amount")
  private BigDecimal flightPriceAmount;
}
