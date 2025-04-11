package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static jakarta.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.ReplicatedJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED, force = true)
@Table(name = "airlines")
@Entity
public class AirlineJpaEntity extends ReplicatedJpaEntity {

  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "active", nullable = false)
  private Boolean active;

  @NonNull
  @OneToMany(cascade = ALL, orphanRemoval = true)
  @JoinColumn(name = "airline_id", referencedColumnName = "id", nullable = false)
  private List<FlightJpaEntity> flights;
}
