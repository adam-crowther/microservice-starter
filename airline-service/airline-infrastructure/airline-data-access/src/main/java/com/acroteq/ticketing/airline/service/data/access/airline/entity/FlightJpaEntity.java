package com.acroteq.ticketing.airline.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "flights")
@Entity
public class FlightJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(cascade = ALL)
  @JoinColumn(name = "airline_id")
  private AirlineJpaEntity airline;

  @Column(name = "flight_number")
  private String flightNumber;

  @Column(name = "price_currency_id", length = 36)
  private Long priceCurrencyId;

  @Column(name = "price_amount")
  private BigDecimal priceAmount;

  @Column(name = "available")
  private Boolean available;
}
