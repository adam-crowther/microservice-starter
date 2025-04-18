package com.acroteq.ticketing.order.service.data.access.customer.entity;

import static lombok.AccessLevel.PROTECTED;

import com.acroteq.infrastructure.data.access.entity.ReplicatedJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "customers")
@Entity
public class CustomerJpaEntity extends ReplicatedJpaEntity {

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;
}
