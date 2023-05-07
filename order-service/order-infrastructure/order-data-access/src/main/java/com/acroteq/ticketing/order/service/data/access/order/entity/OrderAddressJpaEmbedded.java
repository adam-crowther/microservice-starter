package com.acroteq.ticketing.order.service.data.access.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class OrderAddressJpaEmbedded {

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "postal_code", nullable = false)
  private String postalCode;

  @Column(name = "city", nullable = false)
  private String city;
}
