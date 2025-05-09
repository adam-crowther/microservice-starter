package com.acroteq.ticketing.airline.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.PrimaryJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "airlines")
@Entity
public class AirlineJpaEntity extends PrimaryJpaEntity {

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "active", nullable = false)
  private Boolean active;

  @OneToMany(cascade = ALL, orphanRemoval = true)
  @JoinColumn(name = "airline_id", referencedColumnName = "id", nullable = false)
  private List<FlightJpaEntity> flights;
}
