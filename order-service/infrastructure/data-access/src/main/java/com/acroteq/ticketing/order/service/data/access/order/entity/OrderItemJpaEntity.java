package com.acroteq.ticketing.order.service.data.access.order.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.ticketing.infrastructure.data.access.entity.PrimaryJpaEntity;
import com.acroteq.ticketing.order.service.data.access.airline.entity.FlightJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "order_items")
@Entity
public class OrderItemJpaEntity extends PrimaryJpaEntity {

  @ManyToOne(optional = false)
  @JoinColumn(name = "flight_id", nullable = false)
  private FlightJpaEntity flight;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;
}
