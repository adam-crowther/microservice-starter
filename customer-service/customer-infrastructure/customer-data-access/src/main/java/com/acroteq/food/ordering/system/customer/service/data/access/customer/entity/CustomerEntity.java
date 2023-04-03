package com.acroteq.food.ordering.system.customer.service.data.access.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
@Entity
public class CustomerEntity {

  @EqualsAndHashCode.Include
  @Id
  @Column(name = "id", length = 36)
  private String id;
  @Column(name = "user_name")
  private String userName;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
}
