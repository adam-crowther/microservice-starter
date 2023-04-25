package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.ALL;
import static java.util.Collections.unmodifiableList;
import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "active")
  private Boolean active;

  @OneToMany(cascade = ALL)
  @JoinColumn(name = "airline_id", referencedColumnName = "id")
  private List<FlightJpaEntity> flights;

  // JPA does not support ImmutableList
  public List<FlightJpaEntity> getFlights() {
    return unmodifiableList(flights);
  }

  @SuppressWarnings("PublicInnerClass")
  public static class AirlineJpaEntityBuilder {

    public AirlineJpaEntityBuilder flights(final List<FlightJpaEntity> flights) {
      this.flights = List.copyOf(flights);
      return this;
    }
  }
}
