package com.acroteq.ticketing.airline.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.google.common.collect.ImmutableList;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "airlines")
@Entity
public class AirlineJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "active")
  private Boolean active;

  @OneToMany(mappedBy = "airline", cascade = ALL)
  private ImmutableList<FlightJpaEntity> flights;

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineJpaEntityBuilder {

    public AirlineJpaEntityBuilder flights(final List<FlightJpaEntity> flights) {
      this.flights = ImmutableList.copyOf(flights);
      return this;
    }
  }
}
