package com.acroteq.ticketing.approval.service.data.access.airline.entity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

import com.acroteq.ticketing.domain.valueobject.OrderApprovalStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor(access = PRIVATE)
@RequiredArgsConstructor
@Table(name = "order_approval")
@Entity
public class OrderApprovalJpaEntity {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "airline_id", length = 36)
  private Long airlineId;

  @Column(name = "order_id", length = 36)
  private Long orderId;

  @Column(name = "status")

  @Enumerated(STRING)
  private OrderApprovalStatus status;
}
