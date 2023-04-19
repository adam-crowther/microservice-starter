package com.acroteq.ticketing.order.service.data.access.airline.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class AirlineJpaEntityId implements Serializable {

  private Long airlineId;

  private Long flightId;
}
